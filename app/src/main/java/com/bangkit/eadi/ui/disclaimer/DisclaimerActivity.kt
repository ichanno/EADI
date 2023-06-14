package com.bangkit.eadi.ui.disclaimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.bangkit.eadi.databinding.ActivityDisclaimerBinding
import com.bangkit.eadi.ui.instruction.InstructionActivity

class DisclaimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisclaimerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisclaimerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for btn_continue
        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            startActivity(intent)
        }
    }
}