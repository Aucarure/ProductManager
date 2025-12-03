package com.tecsup.productmanager.utils

import android.util.Patterns

object ValidationUtils {

    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= Constants.MIN_PASSWORD_LENGTH
    }

    fun passwordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword && password.isNotBlank()
    }

    fun isValidNombre(nombre: String): Boolean {
        return nombre.length >= Constants.MIN_NOMBRE_LENGTH
    }

    fun isValidPrecio(precio: String): Boolean {
        return try {
            val precioDouble = precio.toDouble()
            precioDouble > 0
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun isValidStock(stock: String): Boolean {
        return try {
            val stockInt = stock.toInt()
            stockInt >= 0
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun isValidCategoria(categoria: String): Boolean {
        return categoria.length >= Constants.MIN_CATEGORIA_LENGTH
    }

    fun getEmailErrorMessage(): String {
        return "Ingrese un email válido"
    }

    fun getPasswordErrorMessage(): String {
        return "La contraseña debe tener al menos ${Constants.MIN_PASSWORD_LENGTH} caracteres"
    }

    fun getPasswordMatchErrorMessage(): String {
        return "Las contraseñas no coinciden"
    }

    fun getNombreErrorMessage(): String {
        return "El nombre debe tener al menos ${Constants.MIN_NOMBRE_LENGTH} caracteres"
    }

    fun getPrecioErrorMessage(): String {
        return "Ingrese un precio válido mayor a 0"
    }

    fun getStockErrorMessage(): String {
        return "Ingrese un stock válido (número entero >= 0)"
    }

    fun getCategoriaErrorMessage(): String {
        return "La categoría debe tener al menos ${Constants.MIN_CATEGORIA_LENGTH} caracteres"
    }
}