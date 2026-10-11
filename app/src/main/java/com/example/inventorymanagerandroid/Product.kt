package com.example.inventorymanagerandroid

/**
 * Represents a product stored in the inventory.
 */
data class Product(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val quantity: Int = 0,
    val minimumStock: Int = 0
)