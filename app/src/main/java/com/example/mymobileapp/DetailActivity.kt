package Com.KittyTeam.Shop

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var productNameTV: TextView
    private lateinit var productPriceTV: TextView
    private lateinit var productDescTV: TextView
    private lateinit var addToCartBtn: Button
    private lateinit var backBtn: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        productNameTV = findViewById(R.id.productNameTV)
        productPriceTV = findViewById(R.id.productPriceTV)
        productDescTV = findViewById(R.id.productDescTV)
        addToCartBtn = findViewById(R.id.addToCartBtn)
        backBtn = findViewById(R.id.backBtn)
        sharedPreferences = getSharedPreferences("ShopHub", MODE_PRIVATE)

        val productName = intent.getStringExtra("productName") ?: "Товар"
        val productPrice = intent.getStringExtra("productPrice") ?: "0 ₽"

        productNameTV.text = "📦 $productName"
        productPriceTV.text = "💰 $productPrice"
        productDescTV.text = "Отличный товар с высоким качеством."

        addToCartBtn.setOnClickListener {
            addToCart(productName, productPrice)
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun addToCart(name: String, price: String) {
        var cartString = sharedPreferences.getString("cart", "") ?: ""
        val newItem = "$name|$price"
        cartString = if (cartString.isEmpty()) newItem else "$cartString;$newItem"
        sharedPreferences.edit().putString("cart", cartString).apply()
        Toast.makeText(this, "✅ $name добавлен в корзину!", Toast.LENGTH_LONG).show()
    }
}
