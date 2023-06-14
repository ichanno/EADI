package com.bangkit.eadi.ui.main

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.eadi.databinding.ActivityMainBinding
import com.bangkit.eadi.ui.about.AboutActivity
import com.bangkit.eadi.ui.disclaimer.DisclaimerActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for iv_camera
        binding.ivCamera.setOnClickListener {
            val intent = Intent(this, DisclaimerActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for iv_about
        binding.ivAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}