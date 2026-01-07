package com.example.mymobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val listView = findViewById<ListView>(R.id.itemsListView)
        val backBtn = findViewById<Button>(R.id.backBtn)

        // Пример списка товаров
        val items = listOf(
            "📱 Смартфон Samsung Galaxy",
            "💻 Ноутбук Dell XPS 13",
            "🎧 Наушники Apple AirPods Pro",
            "⌚ Умные часы Apple Watch",
            "📷 Камера Canon EOS R5",
            "🖨️ Принтер HP LaserJet",
            "⌨️ Механическая клавиатура",
            "🖱️ Беспроводная мышь",
            "📺 Монитор LG UltraWide",
            "🔌 USB-хаб на 7 портов"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items
        )
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("item_name", items[position])
            intent.putExtra("item_position", position)
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}
