package Com.KittyTeam.Shop

import android.Manifest
import android.widget.Button
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        navView = findViewById(R.id.nav_view)
        sharedPreferences = getSharedPreferences("ShopHub", MODE_PRIVATE)

        setSupportActionBar(toolbar)

        // Если пользователь первый раз
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

        // ✅ ОБНОВИТЬ МЕНЮ ПРИ ЗАГРУЗКЕ
        updateNavHeader()

        // ТЕСТ КНОПКА (УДАЛИТЬ ПОСЛЕ ТЕСТА)
        addTestPushButton()

        // ОБРАБОТЧИК DRAWER МЕНЮ
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
                    Toast.makeText(this, "👋 Вы вышли", Toast.LENGTH_SHORT).show()
                    updateNavHeader()  // ✅ ОБНОВИТЬ МЕНЮ!
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }
    }

    // ✅ ФУНКЦИЯ ОБНОВЛЕНИЯ МЕНЮ
    private fun updateNavHeader() {
        val headerView = navView.getHeaderView(0)
        val userNameTV = headerView.findViewById<TextView>(R.id.header_user_name)
        val userEmailTV = headerView.findViewById<TextView>(R.id.header_user_email)

        val name = sharedPreferences.getString("userName", "👤 Гость") ?: "👤 Гость"
        val email = sharedPreferences.getString("userEmail", "guest@shophub.com") ?: "guest@shophub.com"

        userNameTV.text = name
        userEmailTV.text = email
    }

    private fun toggleTheme() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val newMode = if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(newMode)
        val themeName = if (newMode == AppCompatDelegate.MODE_NIGHT_YES) "Тёмная" else "Светлая"
        Toast.makeText(this, "✅ Тема: $themeName", Toast.LENGTH_LONG).show()
        recreate()
    }

    private fun addTestPushButton() {
        val contentContainer = findViewById<LinearLayout>(R.id.content_container)
        val testPushBtn = Button(this)
        testPushBtn.text = "🧪 ТЕСТ PUSH"
        testPushBtn.setOnClickListener { sendTestPush() }
        contentContainer?.addView(testPushBtn)
    }

    private fun sendTestPush() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
                return
            }
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "test_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Test Notifications", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("✅ PUSH РАБОТАЕТ!")
            .setContentText("Firebase подключен к Com.KittyTeam.Shop!")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(999, notification)
        Toast.makeText(this, "🔔 Push отправлен!", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendTestPush()
        }
    }
}
