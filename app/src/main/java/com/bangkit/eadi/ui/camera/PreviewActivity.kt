package com.bangkit.eadi.ui.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.eadi.databinding.ActivityPreviewBinding

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = Uri.parse(imageUriString)

        binding.ivImage.setImageURI(imageUri)

        binding.btnUpload.setOnClickListener {
            // Handle the upload action here
            uploadImage(imageUri)
        }

        binding.btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun uploadImage(imageUri: Uri) {
        // Implement the upload logic here
        // You can use the imageUri to access the selected image and upload it to a server or perform other operations
    }
}
