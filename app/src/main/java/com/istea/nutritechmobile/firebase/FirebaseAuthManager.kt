package com.istea.nutritechmobile.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "FirebaseAuthManager"

class FirebaseAuthManager() {
    private val auth = FirebaseAuth.getInstance()

    fun getAuth(): FirebaseAuth {
        return this.auth
    }

    fun getAuthUser(): String {
        return this.auth.currentUser.toString()
    }
}