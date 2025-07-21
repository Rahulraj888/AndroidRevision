package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.Product
import com.example.myapplication.databinding.ActivityListBinding
import com.example.myapplication.recyclerview.ProductAdapter

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sample data
        val sampleList = List(30) { index ->
            Product(
                id = index,
                name = "Product $index",
                price = (10..100).random() + 0.99
            )
        }

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ProductAdapter(sampleList)
    }
}