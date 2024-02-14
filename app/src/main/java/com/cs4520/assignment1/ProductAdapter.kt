package com.cs4520.assignment1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class ProductAdapter(private val mProduct: List<ProductItem.Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    // Store a member variable for the contacts

    // Provide a direct reference to each of the views within a data item
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val constraintLayout:ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
        val productImage: ImageView = itemView.findViewById(R.id.ivItemImage)
        val productName: TextView = itemView.findViewById(R.id.tvItemName)
        val expiryDate: TextView = itemView.findViewById(R.id.tvExpiryDate)
        val productPrice: TextView = itemView.findViewById(R.id.tvItemPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val productView = inflater.inflate(R.layout.fragment_product_list, parent, false)
        // Return a new holder instance
        return ViewHolder(productView)
    }

    override fun onBindViewHolder(viewHolder: ProductAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val product: ProductItem.Product = mProduct[position]

        // Set background color and image based on product type
        if (product.type == "Equipment") {
            viewHolder.constraintLayout.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.light_red))
            viewHolder.productImage.setImageResource(R.drawable.ic_equipment)
        } else if (product.type == "Food") {
            viewHolder.constraintLayout.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.light_yellow))
            viewHolder.productImage.setImageResource(R.drawable.ic_food)
        }
        // Set item views based on your views and data model
        viewHolder.productName.text = product.name
        if (product.expiryDate === "") {
            val params = viewHolder.productPrice.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = R.id.tvItemName

        }
        viewHolder.expiryDate.text = product.expiryDate
        viewHolder.productPrice.text = "$" + product.price
    }
    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mProduct.size
    }
}