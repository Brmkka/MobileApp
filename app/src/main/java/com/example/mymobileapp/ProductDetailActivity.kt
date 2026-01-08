package Com.KittyTeam.Shop

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productNameTV: TextView
    private lateinit var productPriceTV: TextView
    private lateinit var productDescTV: TextView
    private lateinit var addToCartBtn: Button
    private lateinit var backBtn: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        productNameTV = findViewById(R.id.productNameTV)
        productPriceTV = findViewById(R.id.productPriceTV)
        productDescTV = findViewById(R.id.productDescTV)
        addToCartBtn = findViewById(R.id.addToCartBtn)
        backBtn = findViewById(R.id.backBtn)
        sharedPreferences = getSharedPreferences("ShopHub", MODE_PRIVATE)

        // ✅ ПОЛУЧИТЬ ДАННЫЕ ИЗ INTENT
        val productName = intent.getStringExtra("productName") ?: "Товар"
        val productPrice = intent.getStringExtra("productPrice") ?: "0"

        productNameTV.text = productName
        productPriceTV.text = "💰 $productPrice ₽"
        productDescTV.text = "Описание: $productName\n\nКачественный товар от проверенного производителя. Доставка в течение 2-3 дней."

        // ✅ КНОПКА ДОБАВИТЬ В КОРЗИНУ
        addToCartBtn.setOnClickListener {
            addToCart("$productName|$productPrice")
            Toast.makeText(this, "✅ Добавлено в корзину!", Toast.LENGTH_LONG).show()
            finish()
        }

        backBtn.setOnClickListener { finish() }
    }

    // ✅ ФУНКЦИЯ ДОБАВЛЕНИЯ В КОРЗИНУ
    private fun addToCart(item: String) {
        val cartString = sharedPreferences.getString("cart", "") ?: ""
        val newCart = if (cartString.isEmpty()) {
            item
        } else {
            "$cartString;$item"
        }
        sharedPreferences.edit().putString("cart", newCart).apply()
    }
}
