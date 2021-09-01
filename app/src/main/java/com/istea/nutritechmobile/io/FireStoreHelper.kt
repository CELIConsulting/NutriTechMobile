package com.istea.nutritechmobile.io

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.istea.nutritechmobile.data.Role
import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.data.UserResponse
import kotlinx.coroutines.tasks.await

private const val TAG_ACTIVITY = "FirestoreService"

class FireStoreHelper(context: Context) {
    //Settings
    private var db = FirebaseFirestore.getInstance()
    private val settings = firestoreSettings {
        isPersistenceEnabled = true
    }

    //Collections
    private val usersRef = this.db.collection("Users")
    private val rolesRef = this.db.collection("Roles")

    init {
        FirebaseApp.initializeApp(context)
        db.firestoreSettings = settings
    }

    //TODO: Checking if user exists in the database
    suspend fun getUserWithCredentials(user: User): Boolean {
        var isUserValid: Boolean

        try {
            val snapshot = this.usersRef
                .whereEqualTo("Email", user.mail)
                .whereEqualTo("Password", user.password)
                .get()
                .await()

            isUserValid = snapshot.documents.isNotEmpty()


        } catch (e: Exception) {
            Log.d(TAG_ACTIVITY, "Exception: ${e.message}")
            isUserValid = false
        }

        Log.d(TAG_ACTIVITY, "User is valid? $isUserValid")
        return isUserValid
    }

}

//
//                .addOnSuccessListener { snapshot ->
////                    for (document in snapshot) {
////                        Log.d(TAG_ACTIVITY, "Nombre: ${document.data["Nombre"]}")
////                        Log.d(TAG_ACTIVITY, "Apellido: ${document.data["Apellido"]}")
////                    }
//
//                    isUserValid = true
//                }
//                .addOnFailureListener {
//                    Log.d(TAG_ACTIVITY, "No se encontró al usuario")
//                    isUserValid = false
//                }