package com.example.bai10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bai10.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val msv = intent.getStringExtra("KEY_STUDENT_ID")
        val time = intent.getLongExtra("KEY_TIMESTAMP", 0L)

        binding.tvDetail.text = "MSV: $msv\nTimestamp: $time"

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}