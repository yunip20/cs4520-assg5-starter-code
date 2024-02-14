package com.cs4520.assignment1

sealed class ProductItem {
    data class Product(
        val name: String,
        val expiryDate: String?,
        val price: String,
        val type: String
    ) : ProductItem()

}