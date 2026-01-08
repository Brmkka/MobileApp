package Com.KittyTeam.Shop

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat // ✅ ВАЖНЫЙ ИМПОРТ

class CartActivity : AppCompatActivity() {

    private lateinit var itemsListView: ListView
    private lateinit var clearCartBtn: Button
    private lateinit var backBtn: Button
    private lateinit var totalPriceTV: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        itemsListView = findViewById(R.id.itemsListView)
        clearCartBtn = findViewById(R.id.clearCartBtn)
        backBtn = findViewById(R.id.backBtn)
        totalPriceTV = findViewById(R.id.totalPriceTV)
        sharedPreferences = getSharedPreferences("ShopHub", MODE_PRIVATE)

        loadCart()

        clearCartBtn.setOnClickListener {
            sharedPreferences.edit().putString("cart", "").apply()
            Toast.makeText(this, "🗑️ Корзина очищена!", Toast.LENGTH_SHORT).show()
            loadCart()
        }

        backBtn.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        loadCart()
    }

    private fun loadCart() {
        val cartString = sharedPreferences.getString("cart", "") ?: ""
        val items = if (cartString.isEmpty()) {
            emptyList()
        } else {
            cartString.split(";")
        }

        itemsListView.adapter = CustomCartAdapter(this, items)
        updateTotalPrice(items)
    }

    private fun updateTotalPrice(items: List<String>) {
        var total = 0
        items.forEach { item ->
            val pricePart = item.split("|").getOrNull(1)
            val price = pricePart?.replace(",", "")?.toIntOrNull() ?: 0
            total += price
        }
        totalPriceTV.text = "ИТОГО: $total ₽"
    }
}

class CustomCartAdapter(
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

        // ✅ ИСПРАВЛЕНИЕ: БЕРЁМ ЦВЕТ ИЗ РЕСУРСОВ (ЧЕРНЫЙ ИЛИ БЕЛЫЙ)
        textView1.setTextColor(ContextCompat.getColor(context, R.color.text_primary))

        textView2.text = "💰 $price ₽"
        textView2.textSize = 16f
        textView2.setTextColor(ContextCompat.getColor(context, R.color.accent_teal))

        // ✅ ФОН ТОЖЕ БЕРЁМ ИЗ ТЕМЫ
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.card_background))
        view.setPadding(24, 16, 24, 16)

        return view
    }
}
