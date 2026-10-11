package com.example.inventorymanagerandroid

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

/**
 * Handles communication between the application and Cloud Firestore.
 */
class FirestoreRepository {

    private val database = FirebaseFirestore.getInstance()

    private val productsCollection =
        database.collection("products")

    /**
     * Listens for changes in the products collection.
     */
    fun listenToProducts(
        onProductsChanged: (List<Product>) -> Unit,
        onError: (Exception) -> Unit
    ): ListenerRegistration {

        return productsCollection.addSnapshotListener { snapshot, error ->

            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }

            val products = snapshot?.documents?.mapNotNull { document ->

                document
                    .toObject(Product::class.java)
                    ?.copy(id = document.id)

            } ?: emptyList()

            onProductsChanged(products)
        }
    }

    /**
     * Adds a product to Firestore.
     */
    fun addProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {

        val productData = hashMapOf(
            "name" to product.name,
            "category" to product.category,
            "quantity" to product.quantity,
            "minimumStock" to product.minimumStock
        )

        productsCollection
            .add(productData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    /**
     * Updates the stock quantity of a product.
     */
    fun updateQuantity(
        productId: String,
        newQuantity: Int,
        onError: (Exception) -> Unit
    ) {

        productsCollection
            .document(productId)
            .update("quantity", newQuantity)
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    /**
     * Deletes a product from Firestore.
     */
    fun deleteProduct(
        productId: String,
        onError: (Exception) -> Unit
    ) {

        productsCollection
            .document(productId)
            .delete()
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
}