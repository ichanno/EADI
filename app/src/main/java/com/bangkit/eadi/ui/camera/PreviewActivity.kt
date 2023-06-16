package com.bangkit.eadi.ui.camera

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import com.bangkit.eadi.databinding.ActivityPreviewBinding
import java.io.IOException

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private lateinit var imageUri: Uri

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isCameraPhoto = intent.getBooleanExtra("isCameraPhoto", false)
        if (isCameraPhoto) {
            val photoUriString = intent.getStringExtra("photoUri")
            imageUri = Uri.parse(photoUriString)
        } else {
            val imageUriString = intent.getStringExtra("imageUri")
            imageUri = Uri.parse(imageUriString)
        }

        loadImage()

        binding.btnUpload.setOnClickListener {
            // Handle the upload action here
            uploadImage(imageUri)
        }

        binding.btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun loadImage() {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            val rotatedBitmap = rotateBitmap(bitmap, getRotationDegrees(imageUri))
            binding.ivImage.setImageBitmap(rotatedBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun getRotationDegrees(imageUri: Uri): Float {
        var degrees = 0f
        try {
            contentResolver.openInputStream(imageUri)?.use { inputStream ->
                val exifInterface = ExifInterface(inputStream)
                val orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> degrees = 90f
                    ExifInterface.ORIENTATION_ROTATE_180 -> degrees = 180f
                    ExifInterface.ORIENTATION_ROTATE_270 -> degrees = 270f
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return degrees
    }

    private fun uploadImage(imageUri: Uri) {
        // Implement the upload logic here
        // You can use the imageUri to access the selected image and upload it to a server or perform other operations
    }
}
