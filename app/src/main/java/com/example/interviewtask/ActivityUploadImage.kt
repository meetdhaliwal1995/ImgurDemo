package com.example.interviewtask

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtask.databinding.ActivityUploadImageBinding
import com.example.interviewtask.viewmodel.MainActivityViewModel
import com.example.interviewtask.viewmodel.MainViewModelFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class ActivityUploadImage : AppCompatActivity() {
    private var _binding: ActivityUploadImageBinding? = null
    private val binding get() = _binding
    lateinit var mainActivityViewModel: MainActivityViewModel
    private val REQUEST_CODE = 1888
    var imagePath: File? = null

    private lateinit var player: SimpleExoPlayer

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
        binding?.ivUpload?.setOnClickListener {
            selectImageFromGallery()
        }

        binding?.exoPlayer?.setOnClickListener {
            selectImageFromGallery()
        }

        binding?.btnUpload?.setOnClickListener {
            if (imagePath.toString().contains("Images")){
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imagePath!!)
                val image = MultipartBody.Part.createFormData("image", imagePath!!.name, requestFile)
                mainActivityViewModel.uploadImage(image)
                manageProgressBar(true)
            }else{
                val requestFile = videoFile.asRequestBody("video/*".toMediaTypeOrNull())
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("video", videoFile.name, requestFile)
                    .build()
            }

        }

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

    private fun selectImageFromGallery() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "*/*"
        startActivityForResult(pickIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            Log.e("imageImageee", selectedImage.toString())
            if (selectedImage.toString().contains("video")) {
                binding?.exoPlayer?.visibility = View.VISIBLE
                binding?.ivUpload?.visibility = View.GONE
                initializePlayer(selectedImage!!)
            } else {
                binding?.ivUpload?.visibility = View.VISIBLE
                binding?.exoPlayer?.visibility = View.GONE
                imageUri(selectedImage!!)
            }
        }
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
        Log.e("imagePathhhh", imagePath.toString())


    }

    private fun initializePlayer(videoUri: Uri) {
        val dataSourceFactory =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, "Your Application Name"))
        val mediaItem = MediaItem.fromUri(videoUri)
        val mediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)

        player = SimpleExoPlayer.Builder(this)
            .build()
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
        player.release()
        _binding = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}