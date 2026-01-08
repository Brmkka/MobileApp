package com.example.mymobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {

    private lateinit var itemsListView: ListView
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        itemsListView = findViewById(R.id.itemsListView)
        backBtn = findViewById(R.id.backBtn)

        // ✅ ТОВАРЫ
        val items = listOf(
            "🎮 Ноутбук ASUS|45999",
            "📱 iPhone 15 Pro|89999",
            "⌚ Apple Watch Ultra|34999",
            "🎧 Sony WH-1000XM5|24999",
            "📷 Canon EOS R5|299999",
            "💻 MacBook Air M2|89999",
            "🖥️ LG Ultrawide Monitor|18999",
            "⌨️ Механическая клавиатура|8999",
            "🖱️ Мышь Logitech|5999",
            "🎮 PlayStation 5|45999"
        )

        itemsListView.adapter = CustomListAdapter(this, items)

        // ✅ КЛИК - ПЕРЕХОД НА ДЕТАЛИ ТОВАРА
        itemsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            val parts = selectedItem.split("|")
            val name = parts[0]
            val price = parts[1]

            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("productName", name)
            intent.putExtra("productPrice", price)
            startActivity(intent)
        }

        backBtn.setOnClickListener { finish() }
    }
}

class CustomListAdapter(
    private val context: AppCompatActivity,
    private val items: List<String>
) : BaseAdapter() {

    override fun getCount(): Int = items.size
    override fun getItem(position: Int): String = items[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: android.view.LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)

        val item = items[position]
        val parts = item.split("|")
        val name = parts.getOrNull(0) ?: "Товар"
        val price = parts.getOrNull(1) ?: "0"

        val textView1 = view.findViewById<TextView>(android.R.id.text1)
        val textView2 = view.findViewById<TextView>(android.R.id.text2)

        textView1.text = name
        textView1.textSize = 18f
        textView1.setTextColor(0xFFFFFFFF.toInt())

        textView2.text = "💰 $price ₽"
        textView2.textSize = 16f
        textView2.setTextColor(0xFF20C997.toInt())

        view.setBackgroundColor(0xFF2A2A2A.toInt())
        view.setPadding(24, 16, 24, 16)

        return view
    }
}
