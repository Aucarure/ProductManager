package com.tecsup.productmanager.viewmodel

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthState
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel {
    fun login(email: String, password: String)
    fun register(email: String, password: String)
    fun logout()
    val authState: StateFlow<AuthState>
}
