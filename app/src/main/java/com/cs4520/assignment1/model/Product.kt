package com.cs4520.assignment1.model

sealed class ProductItem {
    class Product(
        val name: String,
        val expiryDate: String?,
        val price: String,
        val type: String
    ) : ProductItem()

}

