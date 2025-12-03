package com.tecsup.productmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.productmanager.data.model.Product
import com.tecsup.productmanager.ui.screens.auth.LoginScreen
import com.tecsup.productmanager.ui.screens.auth.RegisterScreen
import com.tecsup.productmanager.ui.screens.product.AddProductScreen
import com.tecsup.productmanager.ui.screens.product.EditProductScreen
import com.tecsup.productmanager.ui.screens.product.ProductListScreen
import com.tecsup.productmanager.viewmodel.AuthState
import com.tecsup.productmanager.viewmodel.AuthViewModel
import com.tecsup.productmanager.viewmodel.ProductViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val productViewModel: ProductViewModel = viewModel()

    val authState by authViewModel.authState.collectAsState()
    val products by productViewModel.products.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navController.navigate("productList") {
                    popUpTo(0) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginClick = { email, password ->
                    authViewModel.login(email, password)
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterClick = { email, password ->
                    authViewModel.register(email, password)
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("productList") {
            ProductListScreen(
                products = products,
                onAddClick = {
                    navController.navigate("addProduct")
                },
                onEditClick = { product ->
                    navController.navigate("editProduct/${product.id}/${product.nombre}/${product.precio}/${product.stock}/${product.categoria}")
                },
                onDeleteClick = { productId ->
                    productViewModel.deleteProduct(productId)
                },
                onLogoutClick = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable("addProduct") {
            AddProductScreen(
                onSaveClick = { product ->
                    productViewModel.addProduct(product)
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "editProduct/{id}/{nombre}/{precio}/{stock}/{categoria}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType },
                navArgument("stock") { type = NavType.StringType },
                navArgument("categoria") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val product = Product(
                id = backStackEntry.arguments?.getString("id") ?: "",
                nombre = backStackEntry.arguments?.getString("nombre") ?: "",
                precio = backStackEntry.arguments?.getString("precio")?.toDoubleOrNull() ?: 0.0,
                stock = backStackEntry.arguments?.getString("stock")?.toIntOrNull() ?: 0,
                categoria = backStackEntry.arguments?.getString("categoria") ?: ""
            )

            EditProductScreen(
                product = product,
                onUpdateClick = { updatedProduct ->
                    productViewModel.updateProduct(updatedProduct)
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}