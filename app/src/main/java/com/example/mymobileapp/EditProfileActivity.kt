package com.example.mymobileapp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import android.util.Base64

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profileImageIV: ImageView
    private lateinit var changePhotoBtn: Button
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var saveBtn: Button
    private lateinit var cancelBtn: Button
    private lateinit var sharedPreferences: SharedPreferences
    private var selectedImageBase64: String? = null

    companion object {
        private const val GALLERY_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        profileImageIV = findViewById(R.id.profileImageIV)
        changePhotoBtn = findViewById(R.id.changePhotoBtn)
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        saveBtn = findViewById(R.id.saveBtn)
        cancelBtn = findViewById(R.id.cancelBtn)
        sharedPreferences = getSharedPreferences("ShopHub", MODE_PRIVATE)

        loadProfileData()

        // ✅ ВЫБОР ФОТО
        changePhotoBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }

        saveBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()

            when {
                name.isEmpty() -> {
                    Toast.makeText(this, "Заполните имя", Toast.LENGTH_SHORT).show()
                }
                email.isEmpty() -> {
                    Toast.makeText(this, "Заполните email", Toast.LENGTH_SHORT).show()
                }
                phone.isEmpty() -> {
                    Toast.makeText(this, "Заполните телефон", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    sharedPreferences.edit().putString("userName", name).apply()
                    sharedPreferences.edit().putString("userEmail", email).apply()
                    sharedPreferences.edit().putString("userPhone", phone).apply()

                    if (selectedImageBase64 != null) {
                        sharedPreferences.edit().putString("profileImage", selectedImageBase64).apply()
                    }

                    Toast.makeText(this, "✅ Профиль обновлён!", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }

        cancelBtn.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            if (imageUri != null) {
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    profileImageIV.setImageBitmap(bitmap)
                    selectedImageBase64 = bitmapToBase64(bitmap)
                    Toast.makeText(this, "✅ Фото выбрано", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "❌ Ошибка выбора фото", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)
            android.graphics.BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: Exception) {
            null
        }
    }

    private fun loadProfileData() {
        val name = sharedPreferences.getString("userName", "") ?: ""
        val email = sharedPreferences.getString("userEmail", "") ?: ""
        val phone = sharedPreferences.getString("userPhone", "") ?: ""
        val imageBase64 = sharedPreferences.getString("profileImage", null)

        nameInput.setText(name)
        emailInput.setText(email)
        phoneInput.setText(phone)

        if (imageBase64 != null) {
            val bitmap = base64ToBitmap(imageBase64)
            if (bitmap != null) {
                profileImageIV.setImageBitmap(bitmap)
            }
        }
    }
}
