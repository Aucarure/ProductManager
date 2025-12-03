package com.tecsup.productmanager.viewmodel

import com.tecsup.productmanager.data.model.Product
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel {
    fun addProduct(product: Product)
    fun updateProduct(product: Product)
    fun deleteProduct(productId: String)
    fun getProducts()s
    val products: StateFlow<List<Product>>
}