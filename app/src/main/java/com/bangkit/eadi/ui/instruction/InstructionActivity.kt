package com.bangkit.eadi.ui.instruction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.eadi.databinding.ActivityInstructionBinding

class InstructionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstructionBinding

    private val instructions = listOf(
        "Make sure the surrounding environment is quiet and minimal distractions.",
        "Choose a location that is comfortable for the child, with sufficient but not too bright lighting.",
        "Make sure the camera or device used is working properly and is in photo-taking mode.",
        "Give space for children to express themselves naturally.",
        "Don't force your child to smile or pose in a certain way, let them express themselves according to their own personality."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInstructionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView with the custom adapter
        val adapter = InstructionAdapter(instructions)
        binding.rvInstructions.adapter = adapter
        binding.rvInstructions.layoutManager = LinearLayoutManager(this)

        // Set click listener for btn_continue
        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, PhotoInstructionActivity::class.java)
            startActivity(intent)
        }
    }
}