package com.cs4520.assignment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val productList = productsDataset.map {
        Product(it[0] as String, it[2] as String?, "$${it[3]}", it[1] as String)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ProductAdapter(productList)
        return view
    }

    private class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//      val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
        val productName: TextView = itemView.findViewById(R.id.tvProductName)
        val expiryDate: TextView = itemView.findViewById(R.id.tvExpiryDate)
        val productPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val productType: TextView = itemView.findViewById(R.id.tvProductType)
        val productImage: ImageView = itemView.findViewById(R.id.ivProductImage)
    }

    private class ProductAdapter(private val productList: List<Product>) :
        RecyclerView.Adapter<ProductViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_product_list, parent, false)
            return ProductViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val product = productList[position]

            holder.productName.text = product.name
            holder.expiryDate.text = product.expiryDate
            holder.productPrice.text = product.price
            holder.productType.text = product.type

            // Set background color and image based on product type
            if (product.type == "Equipment") {
                holder.constraintLayout.setBackgroundColor(
                    holder.itemView.setBackgroundColor(R.color.light_red)
                )
                holder.productImage.setImageResource(R.drawable.ic_equipment)
            } else if (product.type == "Food") {
                holder.constraintLayout.setBackgroundColor(
                    holder.itemView.setBackgroundColor(R.color.light_yellow)
                )
                holder.productImage.setImageResource(R.drawable.ic_food)
            }

            // Set text color for all text views
            holder.productName.setTextColor(R.color.black)
            holder.expiryDate.setTextColor
            holder.productPrice.setTextColor
            holder.productType.setTextColor
        }

        override fun getItemCount(): Int {
            return productList.size
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}