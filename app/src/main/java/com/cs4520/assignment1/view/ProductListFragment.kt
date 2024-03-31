package com.cs4520.assignment1.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.ProductListViewModel
import com.cs4520.assignment1.model.Product
import com.cs4520.assignment1.model.ProductApiService
import com.cs4520.assignment1.model.ProductDao
import com.cs4520.assignment1.model.ProductDatabase
import com.cs4520.assignment1.model.ProductRepository
import com.cs4520.assignment1.model.RetrofitInstance
import com.cs4520.assignment1.model.productsDataset
import com.cs4520.assignment4.databinding.FragmentProductListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ProductListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductListViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        val view = binding.root
        val productApiService: ProductApiService = RetrofitInstance.apiService
        val repository = ProductRepository(productApiService)
        val db = ProductDatabase.getInstance(requireContext())
        viewModel = ProductListViewModel(repository, db)
        adapter = ProductAdapter(emptyList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.products.observe(viewLifecycleOwner) {products ->
            val distinctProducts = products.distinctBy { product ->
                listOf(product.name, product.expiryDate, product.price, product.type)
            }
            adapter.updateDataSet(distinctProducts)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            val isErrorMessageVisible = errorMessage.isNotEmpty()
            binding.tvErrorMessage.visibility = if (isErrorMessageVisible) { View.VISIBLE } else View.GONE
            binding.tvErrorMessage.text = errorMessage.ifEmpty { "" }
         }
        viewModel.loadProducts(1)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLoading.value!! &&
                    (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                    firstVisibleItemPosition >= 0
                ) {
                    viewModel.loadNextPage()
                }
            }
        })

        viewModel.pageLoadedEvent.observe(viewLifecycleOwner) {pageNum ->
            // Show toast when the next page is loaded
            Toast.makeText(requireContext(), "Page ${pageNum} loaded", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Nullify binding to avoid memory leaks
        _binding = null
    }

}