package com.example.mymobileapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userNameText = findViewById<TextView>(R.id.userNameText)
        val userEmailText = findViewById<TextView>(R.id.userEmailText)
        val joinDateText = findViewById<TextView>(R.id.joinDateText)
        val editBtn = findViewById<Button>(R.id.editBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        // Пример данных (в реальном приложении загружаются с сервера)
        userNameText.text = "Иван Петров"
        userEmailText.text = "ivan@example.com"
        joinDateText.text = "Дата присоединения: 01.12.2024"

        editBtn.setOnClickListener {
            // Может перейти на экран редактирования профиля
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Редактирование")
                .setMessage("Функция редактирования будет доступна в будущей версии")
                .setPositiveButton("ОК", null)
                .show()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}
