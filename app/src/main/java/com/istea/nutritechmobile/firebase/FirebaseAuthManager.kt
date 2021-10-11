package com.istea.nutritechmobile.firebase

import com.google.firebase.auth.FirebaseAuth

private const val TAG = "FirebaseAuthManager"

//FIXME: pasasr todas las operaciones con auth a esta clase.
class FirebaseAuthManager {
    private val auth = FirebaseAuth.getInstance()

    fun getAuth(): FirebaseAuth {
        return this.auth
    }

    fun getAuthUser(): String {
        return this.auth.currentUser.toString()
    }

    fun getAuthEmail(): String {
        return this.auth.currentUser?.email.toString()
    }
}