package com.example.mymobileapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    private lateinit var themeBtn: Button
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        themeBtn = findViewById(R.id.themeBtn)
        backBtn = findViewById(R.id.backBtn)

        themeBtn.setOnClickListener {
            toggleTheme()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun toggleTheme() {
        val sharedPreferences = getSharedPreferences("ShopHub", MODE_PRIVATE)
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val newMode = if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }

        // ✅ СОХРАНИТЬ ВЫБОР
        val isDarkMode = newMode == AppCompatDelegate.MODE_NIGHT_YES
        sharedPreferences.edit().putBoolean("isDarkMode", isDarkMode).apply()

        AppCompatDelegate.setDefaultNightMode(newMode)

        val themeName = if (newMode == AppCompatDelegate.MODE_NIGHT_YES) "Тёмная 🌙" else "Светлая ☀️"
        Toast.makeText(this, "✅ Тема изменена на $themeName", Toast.LENGTH_LONG).show()
        recreate()
    }
}
