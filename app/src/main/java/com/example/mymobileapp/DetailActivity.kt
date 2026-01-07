package com.example.mymobileapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val itemNameText = findViewById<TextView>(R.id.itemNameText)
        val itemDescText = findViewById<TextView>(R.id.itemDescText)
        val priceText = findViewById<TextView>(R.id.priceText)
        val ratingText = findViewById<TextView>(R.id.ratingText)
        val buyBtn = findViewById<Button>(R.id.buyBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        // Получаем данные из Intent
        val itemName = intent.getStringExtra("item_name") ?: "Неизвестный товар"
        val itemPosition = intent.getIntExtra("item_position", 0)

        itemNameText.text = itemName

        // Пример описаний для разных товаров
        val descriptions = listOf(
            "Современный смартфон с AMOLED дисплеем 120Hz и процессором Snapdragon",
            "Легкий ноутбук для работы и творчества с батареей на 20 часов",
            "Премиум наушники с активным шумоподавлением и отличным звуком",
            "Смарт-часы с полноценным ОС watchOS и здоровьем сердца",
            "Профессиональная зеркальная камера для фото и видеосъемки",
            "Быстрый сетевой принтер для офиса и дома",
            "Механическая клавиатура с RGB подсветкой",
            "Эргономичная беспроводная мышь с 8 кнопками",
            "Ультраширокий монитор 34 дюйма для продуктивности",
            "Универсальный хаб для подключения всех устройств"
        )

        val prices = listOf(
            "Цена: 45 000 ₽",
            "Цена: 120 000 ₽",
            "Цена: 28 000 ₽",
            "Цена: 35 000 ₽",
            "Цена: 280 000 ₽",
            "Цена: 18 000 ₽",
            "Цена: 12 000 ₽",
            "Цена: 3 500 ₽",
            "Цена: 65 000 ₽",
            "Цена: 2 500 ₽"
        )

        itemDescText.text = if (itemPosition < descriptions.size) descriptions[itemPosition] else "Описание товара"
        priceText.text = if (itemPosition < prices.size) prices[itemPosition] else "Цена: не указана"
        ratingText.text = "⭐ Рейтинг: 4.5/5 (${100 + itemPosition * 50} отзывов)"

        buyBtn.setOnClickListener {
            Toast.makeText(this, "Товар добавлен в корзину!", Toast.LENGTH_SHORT).show()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}
