package com.example.interviewtask

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtask.databinding.ActivityUploadImageBinding
import com.example.interviewtask.viewmodel.MainActivityViewModel
import com.example.interviewtask.viewmodel.MainViewModelFactory
import okhttp3.MediaType
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

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

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

        binding?.btnUpload?.setOnClickListener {
            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imagePath!!)
            val image = MultipartBody.Part.createFormData("image", imagePath?.name, requestFile)
            mainActivityViewModel.uploadImage(image)
            manageProgressBar(true)

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
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data
            imageUri(selectedImage!!)
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
        _binding = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}