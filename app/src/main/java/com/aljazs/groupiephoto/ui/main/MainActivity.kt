package com.aljazs.groupiephoto.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aljazs.groupiephoto.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.text1.text = "bananarama"
    }
}