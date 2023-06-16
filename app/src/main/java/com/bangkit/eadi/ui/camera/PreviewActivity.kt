package com.bangkit.eadi.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import com.bangkit.eadi.databinding.ActivityPreviewBinding
import com.bangkit.eadi.ui.apihelper.ApiClient
import com.bangkit.eadi.ui.apihelper.ApiResponse
import com.bangkit.eadi.ui.result.ResultActivity
import com.bangkit.eadi.ui.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private lateinit var imageUri: Uri

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra("imageUri")
        imageUri = Uri.parse(imageUriString)

        loadImage()

        binding.btnUpload.setOnClickListener {
            uploadImage(imageUri)
        }

        binding.btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    @Suppress("DEPRECATION")
    private fun loadImage() {
        Log.d("PreviewActivity", "Image URI: $imageUri")
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
        val apiService = ApiClient.create()
        val file = uriToFile(imageUri, this)

        if (file != null) {
            val reqImgFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image", file.name, reqImgFile
            )

            val call: Call<ApiResponse> = apiService.uploadImage(imageMultipart)
            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val predictedLabel = response.body()?.predictedLabel
                        val intent = Intent(this@PreviewActivity, ResultActivity::class.java)
                        intent.putExtra("verdict", predictedLabel)
                        startActivity(intent)
                    } else {
                        // Handle error response
                        val errorMessage = response.message()
                        // You can handle the error based on the response code or message
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // Handle network failure
                    t.printStackTrace()
                }
            })
        }
    }


}
