package com.bangkit.eadi.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.activity.result.contract.ActivityResultContracts
import com.bangkit.eadi.databinding.ActivityCameraSelectionBinding

class CameraSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraSelectionBinding

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startCameraX()
        } else {
            Toast.makeText(
                this@CameraSelectionActivity,
                "Camera permission denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llCamera.setOnClickListener {
            if (checkCameraPermission()) {
                startCameraX()
            } else {
                requestCameraPermission()
            }
        }

        binding.llGallery.setOnClickListener {
            startGallery()
        }

    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST
        )
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Obtain the captured image URI
            val imageUri = result.data?.data
            if (imageUri != null) {
                startPreviewActivity(imageUri)
            } else {
                Toast.makeText(
                    this@CameraSelectionActivity,
                    "Failed to capture image",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        launcherIntentGallery.launch(intent)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            if (imageUri != null) {
                startPreviewActivity(imageUri)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun startPreviewActivity(imageUri: Uri) {
        val intent = Intent(this, PreviewActivity::class.java)
        intent.putExtra("imageUri", imageUri.toString())
        startActivityForResult(intent, PREVIEW_REQUEST)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraX()
            } else {
                Toast.makeText(
                    this@CameraSelectionActivity,
                    "Camera permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 100
        private const val PREVIEW_REQUEST = 200
    }
}
