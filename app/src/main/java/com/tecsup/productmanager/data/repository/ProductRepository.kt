package com.tecsup.productmanager.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.tecsup.productmanager.data.model.Product
import com.tecsup.productmanager.utils.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val productsCollection = db.collection(Constants.PRODUCTS_COLLECTION)

    // Obtener productos en tiempo real
    fun getProducts(): Flow<List<Product>> = callbackFlow {
        val subscription = productsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val products = snapshot.documents.mapNotNull { doc ->
                    doc.toObject<Product>()?.copy(id = doc.id)
                }
                trySend(products)
            }
        }

        awaitClose { subscription.remove() }
    }

    // Agregar producto
    suspend fun addProduct(product: Product): Result<String> {
        return try {
            val docRef = productsCollection.document()
            val newProduct = product.copy(id = docRef.id)
            docRef.set(newProduct.toMap()).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Actualizar producto
    suspend fun updateProduct(product: Product): Result<Unit> {
        return try {
            productsCollection.document(product.id)
                .set(product.toMap())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Eliminar producto
    suspend fun deleteProduct(productId: String): Result<Unit> {
        return try {
            productsCollection.document(productId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}