package com.bangkit.eadi.ui.instruction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.eadi.databinding.ActivityPhotoInstructionBinding
import com.bangkit.eadi.ui.camera.CameraSelectionActivity

class PhotoInstructionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoInstructionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoInstructionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for btn_continue
        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, CameraSelectionActivity::class.java)
            startActivity(intent)
        }
    }
}