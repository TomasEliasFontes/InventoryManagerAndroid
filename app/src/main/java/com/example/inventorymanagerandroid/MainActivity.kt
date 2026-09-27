package com.example.inventorymanagerandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inventorymanagerandroid.ui.theme.InventoryManagerAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            InventoryManagerAndroidTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    InventoryApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun InventoryApp(modifier: Modifier = Modifier) {

    val products = remember {
        mutableStateListOf(
            Product(1, "Coffee", "Food", 15, 5),
            Product(2, "Notebook", "Office", 30, 10),
            Product(3, "Tea", "Food", 8, 5)
        )
    }

    var currentScreen by remember {
        mutableStateOf("inventory")
    }

    when (currentScreen) {

        "inventory" -> {
            InventoryScreen(
                products = products,
                onAddProductClick = {
                    currentScreen = "add"
                },
                modifier = modifier
            )
        }

        "add" -> {
            AddProductScreen(
                onSaveProduct = { name, category, quantity, minimumStock ->

                    val newId =
                        (products.maxOfOrNull { it.id } ?: 0) + 1

                    products.add(
                        Product(
                            id = newId,
                            name = name,
                            category = category,
                            quantity = quantity,
                            minimumStock = minimumStock
                        )
                    )

                    currentScreen = "inventory"
                },

                onCancel = {
                    currentScreen = "inventory"
                },

                modifier = modifier
            )
        }
    }
}

@Composable
fun InventoryScreen(
    products: SnapshotStateList<Product>,
    onAddProductClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text(
                text = "Inventory Manager",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        items(
            items = products,
            key = { product -> product.id }
        ) { product ->

            ProductCard(
                product = product,

                onIncreaseStock = {
                    val index = products.indexOfFirst {
                        it.id == product.id
                    }

                    if (index != -1) {
                        products[index] = product.copy(
                            quantity = product.quantity + 1
                        )
                    }
                },

                onDecreaseStock = {
                    val index = products.indexOfFirst {
                        it.id == product.id
                    }

                    if (index != -1 && product.quantity > 0) {
                        products[index] = product.copy(
                            quantity = product.quantity - 1
                        )
                    }
                }
            )
        }

        item {
            Button(
                onClick = onAddProductClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Product")
            }
        }
    }
}

@Composable
fun AddProductScreen(
    onSaveProduct: (
        String,
        String,
        Int,
        Int
    ) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {

    var name by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    var quantity by remember {
        mutableStateOf("")
    }

    var minimumStock by remember {
        mutableStateOf("")
    }

    val quantityNumber = quantity.toIntOrNull()
    val minimumStockNumber = minimumStock.toIntOrNull()

    val formIsValid =
        name.isNotBlank() &&
                category.isNotBlank() &&
                quantityNumber != null &&
                quantityNumber >= 0 &&
                minimumStockNumber != null &&
                minimumStockNumber >= 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Add Product",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Product name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = category,
            onValueChange = {
                category = it
            },
            label = {
                Text("Category")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = {
                quantity = it
            },
            label = {
                Text("Quantity")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = minimumStock,
            onValueChange = {
                minimumStock = it
            },
            label = {
                Text("Minimum stock")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onSaveProduct(
                    name,
                    category,
                    quantityNumber!!,
                    minimumStockNumber!!
                )
            },
            enabled = formIsValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Product")
        }

        Button(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onIncreaseStock: () -> Unit,
    onDecreaseStock: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = "Category: ${product.category}")
            Text(text = "Stock: ${product.quantity}")
            Text(text = "Minimum stock: ${product.minimumStock}")

            if (product.quantity < product.minimumStock) {
                Text(
                    text = "LOW STOCK",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = onDecreaseStock,
                    enabled = product.quantity > 0
                ) {
                    Text("-")
                }

                Button(
                    onClick = onIncreaseStock
                ) {
                    Text("+")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryAppPreview() {
    InventoryManagerAndroidTheme {
        InventoryApp()
    }
}