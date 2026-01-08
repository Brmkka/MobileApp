package Com.KittyTeam.Shop

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameET: EditText
    private lateinit var emailET: EditText
    private lateinit var phoneET: EditText
    private lateinit var passwordET: EditText
    private lateinit var registerBtn: Button
    private lateinit var backBtn: Button
    private lateinit var sharedPreferences: SharedPreferences
    private val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameET = findViewById(R.id.nameET)
        emailET = findViewById(R.id.emailET)
        phoneET = findViewById(R.id.phoneET)
        passwordET = findViewById(R.id.passwordET)
        registerBtn = findViewById(R.id.registerBtn)
        backBtn = findViewById(R.id.backBtn)
        sharedPreferences = getSharedPreferences("ShopHub", MODE_PRIVATE)

        registerBtn.setOnClickListener {
            registerUser()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun registerUser() {
        val name = nameET.text.toString().trim()
        val email = emailET.text.toString().trim()
        val phone = phoneET.text.toString().trim()
        val password = passwordET.text.toString().trim()

        // Валидация
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Некорректный email!", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Пароль должен быть минимум 6 символов!", Toast.LENGTH_SHORT).show()
            return
        }

        // Сохранить локально
        sharedPreferences.edit()
            .putString("userName", name)
            .putString("userEmail", email)
            .putString("userPhone", phone)
            .putBoolean("isLoggedIn", true)
            .apply()

        // Отправить на сервер (пример)
        sendToServer(name, email, phone, password)
    }

    private fun sendToServer(name: String, email: String, phone: String, password: String) {
        val json = """
            {
                "name": "$name",
                "email": "$email",
                "phone": "$phone",
                "password": "$password"
            }
        """.trimIndent()

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = json.toRequestBody(mediaType)

        // ЗАМЕНИ НА СВОЙ URL СЕРВЕРА!
        val request = Request.Builder()
            .url("https://your-server.com/api/register")
            .post(body)
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Ошибка подключения: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Регистрация успешна!",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Перейти на главную
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Ошибка регистрации: ${response.code}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }
}
