package com.tecsup.productmanager.ui.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.productmanager.data.model.Product
import com.tecsup.productmanager.ui.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    products: List<Product>,
    onAddClick: () -> Unit,
    onEditClick: (Product) -> Unit,
    onDeleteClick: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Productos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Salir",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color.Blue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray)
        ) {
            if (products.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No hay productos", style = MaterialTheme.typography.titleLarge)
                    Text("Presiona + para agregar uno")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(products) { product ->
                        ProductCard(
                            product = product,
                            onEditClick = { onEditClick(product) },
                            onDeleteClick = { onDeleteClick(product.id) }
                        )
                    }
                }
            }
        }
    }
}