package com.bangkit.eadi.ui.camera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.eadi.databinding.ActivityCameraSelectionBinding

class CameraSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for ll_camera
        binding.llCamera.setOnClickListener {
            openCamera()
        }

        // Set click listener for ll_gallery
        binding.llGallery.setOnClickListener {
            openGallery()
        }
    }

    private fun openCamera() {
        // Handle camera selection logic here
        // For example, you can start an activity to open the camera or perform any other action
        // Replace ExampleActivity with the actual activity to open the camera
        val intent = Intent(this, ExampleActivity::class.java)
        startActivity(intent)
    }

    private fun openGallery() {
        // Handle gallery selection logic here
        // For example, you can start an activity to open the gallery or perform any other action
        // Replace ExampleActivity with the actual activity to open the gallery
        val intent = Intent(this, ExampleActivity::class.java)
        startActivity(intent)
    }
}