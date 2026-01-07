package com.example.mymobileapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var notificationsSwitch: Switch
    private lateinit var prefsText: TextView
    private lateinit var backBtn: Button
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        notificationsSwitch = findViewById(R.id.notificationsSwitch)
        prefsText = findViewById(R.id.prefsText)
        backBtn = findViewById(R.id.backBtn)
        sharedPref = getSharedPreferences("AppSettings", MODE_PRIVATE)

        // Загружаем сохраненное значение
        val notificationsEnabled = sharedPref.getBoolean("notifications", true)
        notificationsSwitch.isChecked = notificationsEnabled
        updateNotificationStatus()

        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("notifications", isChecked).apply()
            updateNotificationStatus()
            val message = if (isChecked) "Пуш-уведомления включены" else "Пуш-уведомления отключены"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun updateNotificationStatus() {
        val status = if (notificationsSwitch.isChecked) "Включены ✓" else "Отключены ✗"
        prefsText.text = "Статус пуш-уведомлений: $status"
    }
}
