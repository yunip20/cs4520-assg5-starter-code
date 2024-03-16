package com.cs4520.assignment1.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.model.Product
import com.cs4520.assignment1.model.ProductItem
import com.cs4520.assignment4.R
import com.cs4520.assignment4.databinding.FragmentProductAdapterBinding

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class ProductAdapter(private var mProduct: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    // Provide a direct reference to each of the views within a data item
    inner class ViewHolder(private val binding: FragmentProductAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a r
        fun bind(product:Product) {
            // Bind data to views using ViewBinding
            binding.apply {
                tvItemName.text = product.name
                tvExpiryDate.text = product.expiryDate
                tvItemPrice.text = "$" + product.price

                if (product.expiryDate === "") {
                    val params = tvItemPrice.layoutParams as ConstraintLayout.LayoutParams
                    params.topToBottom = tvItemName.bottom
                }
                // Set background color and image based on product type
                if (product.type == "Equipment") {
                    productAdapter.setBackgroundColor(ContextCompat.getColor(root.context,R.color.light_red))
                    ivItemImage.setImageResource(R.drawable.ic_equipment)
                } else if (product.type == "Food") {
                    productAdapter.setBackgroundColor(ContextCompat.getColor(root.context, R.color.light_yellow))
                    ivItemImage.setImageResource(R.drawable.ic_food)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentProductAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Return a new holder instance
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get the data model based on position
        val product: Product = mProduct[position]
        viewHolder.bind(product)
    }

    override fun getItemCount(): Int {
        return mProduct.size
    }

    fun updateDataSet(newDataSet: List<Product>) {
        mProduct = newDataSet
        notifyDataSetChanged() // Notify adapter that dataset has changed
    }
}