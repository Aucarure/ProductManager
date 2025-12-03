package com.tecsup.productmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.productmanager.data.model.Product
import com.tecsup.productmanager.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ProductState {
    object Idle : ProductState()
    object Loading : ProductState()
    object Success : ProductState()
    data class Error(val message: String) : ProductState()
}

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _productState = MutableStateFlow<ProductState>(ProductState.Idle)
    val productState: StateFlow<ProductState> = _productState.asStateFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            repository.getProducts().collect { productList ->
                _products.value = productList
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            _productState.value = ProductState.Loading
            val result = repository.addProduct(product)
            _productState.value = if (result.isSuccess) {
                ProductState.Success
            } else {
                ProductState.Error(
                    result.exceptionOrNull()?.message ?: "Error al agregar producto"
                )
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            _productState.value = ProductState.Loading
            val result = repository.updateProduct(product)
            _productState.value = if (result.isSuccess) {
                ProductState.Success
            } else {
                ProductState.Error(
                    result.exceptionOrNull()?.message ?: "Error al actualizar producto"
                )
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            _productState.value = ProductState.Loading
            val result = repository.deleteProduct(productId)
            _productState.value = if (result.isSuccess) {
                ProductState.Success
            } else {
                ProductState.Error(
                    result.exceptionOrNull()?.message ?: "Error al eliminar producto"
                )
            }
        }
    }

    fun resetState() {
        _productState.value = ProductState.Idle
    }
}