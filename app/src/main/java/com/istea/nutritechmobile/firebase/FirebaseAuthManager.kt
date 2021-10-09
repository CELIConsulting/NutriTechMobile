package com.istea.nutritechmobile.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.istea.nutritechmobile.helpers.CameraManager

private const val TAG = "FirebaseAuthManager"

//FIXME: pasasr todas las operaciones con auth a esta clase.
class FirebaseAuthManager() {
    private val auth = FirebaseAuth.getInstance()

    fun getAuth(): FirebaseAuth {
        return this.auth
    }

    fun getAuthUser(): String {
        return this.auth.currentUser.toString()
    }
}