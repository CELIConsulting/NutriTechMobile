package com.istea.nutritechmobile.io

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.firestore.ktx.toObject
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

    suspend fun getUserWithCredentials(user: User): UserResponse? {

        try {
            val snapshot = this.usersRef
                .whereEqualTo("Email", user.mail)
                .whereEqualTo("Password", user.password)
                .get()
                .await()

            //Generar user a partir del snapshot
            if (snapshot.documents.isNotEmpty()) {
                val userSnapshot = snapshot.documents.first()

                //TODO: FIX USER EMPTY
                val userResponse = userSnapshot.toObject<UserResponse>()

                Log.d(
                    TAG_ACTIVITY,
                    "Nombre: ${userResponse?.nombre} | Apellido: ${userResponse?.apellido}"
                )

                if (userResponse != null) {
                    Log.d(TAG_ACTIVITY, "USER NOT NULL")
                    return userResponse
                }
            }

        } catch (e: Exception) {
            Log.d(TAG_ACTIVITY, "Exception: ${e.message}")
        }

        Log.d(TAG_ACTIVITY, "USER NULL")
        return null
    }

}
