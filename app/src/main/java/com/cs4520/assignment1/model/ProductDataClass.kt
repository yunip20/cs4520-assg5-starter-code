package com.cs4520.assignment1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    val expiryDate: String?,
    val price: String?,
    val type: String?
)