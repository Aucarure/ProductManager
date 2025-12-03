package com.tecsup.productmanager.ui.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.productmanager.data.model.Product
import com.tecsup.productmanager.ui.components.CustomTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    onSaveClick: (Product) -> Unit,
    onBackClick: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CustomTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = "Nombre del Producto"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = "Precio",
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = stock,
                        onValueChange = { stock = it },
                        label = "Stock",
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = categoria,
                        onValueChange = { categoria = it },
                        label = "Categoría"
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val product = Product(
                                nombre = nombre,
                                precio = precio.toDoubleOrNull() ?: 0.0,
                                stock = stock.toIntOrNull() ?: 0,
                                categoria = categoria
                            )
                            onSaveClick(product)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text("Guardar Producto")
                    }
                }
            }
        }
    }
}