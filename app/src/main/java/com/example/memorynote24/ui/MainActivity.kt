package com.example.memorynote24.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memorynote24.R
import com.example.memorynote24.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

    }
}