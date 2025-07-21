package com.example.myapplication.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Product
import com.example.myapplication.databinding.ItemProductBinding

class ProductAdapter(private val items: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(
        val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size;

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = items[position]
        holder.binding.textName.text = product.name
        holder.binding.textPrice.text = "$${product.price}"
    }


}