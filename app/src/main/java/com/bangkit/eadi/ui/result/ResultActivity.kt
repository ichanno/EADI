package com.bangkit.eadi.ui.result

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.bangkit.eadi.R
import com.bangkit.eadi.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra("imageUri")
        val verdict = intent.getStringExtra("verdict")
        imageUri = Uri.parse(imageUriString)

        loadImage()
        binding.tvVerdict.text = verdict
    }

    @Suppress("DEPRECATION")
    private fun loadImage() {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            binding.ivImage.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
