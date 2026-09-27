package com.example.inventorymanagerandroid

/**
 * Represents a product in the inventory.
 */
data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val quantity: Int,
    val minimumStock: Int
)