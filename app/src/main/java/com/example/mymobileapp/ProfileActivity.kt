package com.example.mymobileapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var userNameTV: TextView
    private lateinit var userEmailTV: TextView
    private lateinit var userPhoneTV: TextView
    private lateinit var editBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var backBtn: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userNameTV = findViewById(R.id.userNameTV)
        userEmailTV = findViewById(R.id.userEmailTV)
        userPhoneTV = findViewById(R.id.userPhoneTV)
        editBtn = findViewById(R.id.editBtn)
        registerBtn = findViewById(R.id.registerBtn)
        backBtn = findViewById(R.id.backBtn)
        sharedPreferences = getSharedPreferences("ShopHub", MODE_PRIVATE)

        loadProfileData()

        editBtn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // ✅ КНОПКА РЕГИСТРАЦИИ (ДОЛЖНА РАБОТАТЬ!)
        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadProfileData()
    }

    private fun loadProfileData() {
        val name = sharedPreferences.getString("userName", "Гость") ?: "Гость"
        val email = sharedPreferences.getString("userEmail", "не указан") ?: "не указан"
        val phone = sharedPreferences.getString("userPhone", "+7 (999) 999-99-99") ?: "+7 (999) 999-99-99"

        userNameTV.text = "👤 $name"
        userEmailTV.text = "📧 $email"
        userPhoneTV.text = "☎️ $phone"
    }
}
