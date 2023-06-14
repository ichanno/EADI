package com.bangkit.eadi.ui.instruction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.eadi.databinding.ActivityInstructionBinding
import com.bangkit.eadi.ui.camera.CameraSelectionActivity

class InstructionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstructionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInstructionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for btn_continue
        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, CameraSelectionActivity::class.java)
            startActivity(intent)
        }
    }
}