package com.example.bai10

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.bai10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Bộ chọn ảnh từ thư viện
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            processAvatarUri(uri)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnLoadStudent.setOnClickListener {
                displayStudent("Cao Phạm Nhật Quang", 3.6, "cpnq@student.edu.vn")
            }
            btnOpenDetail.setOnClickListener {
                openDetailActivity("2415053122231")
            }
            btnPickAvatar.setOnClickListener {
                pickImage.launch("image/*")
            }
            btnCalc.setOnClickListener {
                val result = calculateAndAudit(3.5)
                tvGpa.text = "Điểm hệ 10: $result"
            }
        }
    }

    // ── 1. with(binding) ────────────────────────────────
    private fun displayStudent(name: String, gpa: Double, email: String) {
        with(binding) {
            tvName.text = name
            tvGpa.text = "Điểm tích lũy: $gpa"
            tvEmail.text = email
            btnUpdate.isEnabled = true
            progressBar.visibility = View.GONE
        }
    }

    // ── 2. apply ────────────────────────────────────────
    private fun openDetailActivity(studentId: String) {
        val detailIntent = Intent(this, DetailActivity::class.java).apply {
            putExtra("KEY_STUDENT_ID", studentId)
            putExtra("KEY_TIMESTAMP", System.currentTimeMillis())
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(detailIntent)
    }

    // ── 3. ?.let { } ?: run { } ─────────────────────────
    private fun processAvatarUri(avatarUri: Uri?) {
        avatarUri?.let { validUri ->
            binding.imgAvatar.setImageURI(validUri)
            binding.tvAvatarStatus.text = "Đã tải ảnh đại diện!"
            toast("Ảnh đã được cập nhật")
        } ?: run {
            binding.imgAvatar.setImageResource(R.drawable.ic_default_avatar)
            binding.tvAvatarStatus.text = "Chưa có ảnh"
        }
    }

    // ── 4. also ─────────────────────────────────────────
    private fun calculateAndAudit(rawScore: Double): Double {
        return (rawScore * 10.0 / 4.0)
            .also { finalScore ->
                Log.d("STUDENT_AUDIT", "Điểm hệ 10 quy đổi: $finalScore")
            }
            .also {
                toast("Đã tính xong điểm: $it")
            }
    }

    // Hàm toast mà code gốc của bạn dùng nhưng chưa định nghĩa
    private fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
//khong co gi het