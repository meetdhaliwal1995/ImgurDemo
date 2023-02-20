package com.example.interviewtask

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtask.databinding.ActivityUploadImageBinding
import com.example.interviewtask.viewmodel.MainActivityViewModel
import com.example.interviewtask.viewmodel.MainViewModelFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import javax.inject.Inject


class ActivityUploadImage : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityUploadImageBinding? = null
    private val binding get() = _binding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val REQUEST_CODE = 1888
    private var imagePath: File? = null
    private var selectedImage: Uri? = null
    private lateinit var player: ExoPlayer

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUploadImageBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        (application as ImageApplication).applicationComponent.injectUploadImage(this)

        mainActivityViewModel =
            ViewModelProvider(this, mainViewModelFactory)[MainActivityViewModel::class.java]

        bindObservers()
        player = ExoPlayer.Builder(this).build()
        binding?.ivUpload?.setOnClickListener(this)
        binding?.btnUpload?.setOnClickListener(this)
        binding?.exoPlayer?.setOnClickListener(this)
    }

    private fun createMultipartBodyPartFromUri(uri: Uri, video: String): MultipartBody.Part {
        val actualPath = getRealPathFromUri(uri) // replace with your own method
        val file = File(actualPath)
        val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(video, file.name, requestBody)
    }

    private fun bindObservers() {
        mainActivityViewModel.uploadImage.observe(this) {
            manageProgressBar(false)
            if (it?.success == true) {
                Toast.makeText(this, "Upload Succesfull", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("IntentReset")
    private fun accessGallery() {
        if (player.isPlaying) {
            player.stop()
        }
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "*/*"
        startActivityForResult(pickIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data
            if (selectedImage.toString().contains("video")) {
                binding?.exoPlayer?.visibility = View.VISIBLE
                binding?.ivUpload?.visibility = View.GONE
                initializePlayer(selectedImage!!)
            } else if (selectedImage.toString().contains("image")) {
                binding?.ivUpload?.visibility = View.VISIBLE
                binding?.exoPlayer?.visibility = View.GONE
                imageUri(selectedImage!!)
            }
        }
    }


    private fun getRealPathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = application.contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DATA) ?: 0
        cursor?.moveToFirst()
        val path = cursor?.getString(columnIndex) ?: ""
        cursor?.close()
        return path
    }

    private fun imageUri(uri: Uri) {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val filePath = cursor.getString(columnIndex)
        cursor.close()
        imagePath = File(filePath)
        binding?.ivUpload?.setImageURI(Uri.fromFile(imagePath))
    }

    private fun initializePlayer(videoUri: Uri) {
        val dataSourceFactory =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, "Your Application Name"))
        val mediaItem = MediaItem.fromUri(videoUri)
        val mediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)


        player.playWhenReady = true
        player.repeatMode = Player.REPEAT_MODE_ONE
        player.setMediaSource(mediaSource)
        player.prepare()

        binding?.exoPlayer?.player = player
    }


    private fun manageProgressBar(show: Boolean) {
        if (show) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        _binding = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (player.isPlaying) {
            player.release()
        }
        finish()
    }

    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        } else {
            accessGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessGallery()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUpload -> {
                if (imagePath.toString().contains("Images")) {
                    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imagePath!!)
                    val image =
                        MultipartBody.Part.createFormData("image", imagePath!!.name, requestFile)
                    mainActivityViewModel.uploadImage(image)
                    manageProgressBar(true)
                } else {
                    val part = createMultipartBodyPartFromUri(selectedImage!!, "video")
                    mainActivityViewModel.uploadImage(part)
                }
            }
            R.id.ivUpload -> {
                requestStoragePermission()
            }
            R.id.exoPlayer -> {
                requestStoragePermission()
            }
        }
    }
}