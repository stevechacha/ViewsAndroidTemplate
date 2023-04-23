package com.chacha.viewsandroidtemplate.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chacha.viewsandroidtemplate.R
import com.chacha.viewsandroidtemplate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}