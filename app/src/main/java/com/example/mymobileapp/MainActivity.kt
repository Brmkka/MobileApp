package com.example.mymobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profileBtn = findViewById<Button>(R.id.profileBtn)
        val listBtn = findViewById<Button>(R.id.listBtn)
        val settingsBtn = findViewById<Button>(R.id.settingsBtn)
        val helpBtn = findViewById<Button>(R.id.helpBtn)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        listBtn.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        settingsBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        helpBtn.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }

        logoutBtn.setOnClickListener {
            Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
