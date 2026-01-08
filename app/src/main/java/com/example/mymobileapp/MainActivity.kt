package com.example.mymobileapp

import com.example.mymobileapp.R
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ ПРИМЕНИТЬ СОХРАНЁННУЮ ТЕМУ ДО ЗАГРУЗКИ МАКЕТА
        sharedPreferences = getSharedPreferences("ShopHub", MODE_PRIVATE)
        loadThemePreference()

        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navView = findViewById(R.id.nav_view)

        setSupportActionBar(toolbar)

        // ✅ Если пользователь первый раз, установим стандартные значения
        if (!sharedPreferences.contains("isLoggedIn")) {
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
            sharedPreferences.edit().putString("userName", "Гость").apply()
            sharedPreferences.edit().putString("userEmail", "guest@shophub.com").apply()
            sharedPreferences.edit().putString("userPhone", "+7 (999) 999-99-99").apply()
        }

        // Drawer Toggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.drawer_open, R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // ✅ ОБРАБОТЧИК DRAWER МЕНЮ
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "🏠 Главная", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_catalog -> {
                    val intent = Intent(this, ListActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_theme -> {
                    // ✅ ПЕРЕКЛЮЧЕНИЕ ТЕМЫ!
                    toggleTheme()
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_help -> {
                    val intent = Intent(this, HelpActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_logout -> {
                    sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
                    sharedPreferences.edit().putString("userName", "Гость").apply()
                    Toast.makeText(this, "👋 Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }
    }

    // ✅ ЗАГРУЗИТЬ СОХРАНЁННУЮ ТЕМУ
    private fun loadThemePreference() {
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
        val mode = if (isDarkMode) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    // ✅ ФУНКЦИЯ ПЕРЕКЛЮЧЕНИЯ ТЕМЫ
    private fun toggleTheme() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val newMode = if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }

        // ✅ СОХРАНИТЬ ВЫБОР ТЕМЫ
        val isDarkMode = newMode == AppCompatDelegate.MODE_NIGHT_YES
        sharedPreferences.edit().putBoolean("isDarkMode", isDarkMode).apply()

        AppCompatDelegate.setDefaultNightMode(newMode)

        val themeName = if (newMode == AppCompatDelegate.MODE_NIGHT_YES) "Тёмная 🌙" else "Светлая ☀️"
        Toast.makeText(this, "✅ Тема изменена на $themeName", Toast.LENGTH_LONG).show()

        // Пересоздать Activity для применения темы
        recreate()
    }
}
