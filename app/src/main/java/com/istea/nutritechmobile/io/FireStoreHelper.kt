package com.istea.nutritechmobile.io

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.istea.nutritechmobile.data.Plan
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
    private val planRef = this.db.collection("Planes")

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
                val fetchedUser = userSnapshot.toObject(UserResponse::class.java)

                if (fetchedUser != null) {
                    Log.d(TAG_ACTIVITY, "Nombre: ${fetchedUser.Nombre}")
                    Log.d(TAG_ACTIVITY, "Apellido: ${fetchedUser.Apellido}")
                    Log.d(TAG_ACTIVITY, "Mail: ${fetchedUser.Email}")
                    Log.d(TAG_ACTIVITY, "Password: ${fetchedUser.Password}")
                    Log.d(TAG_ACTIVITY, "Timestamp: ${fetchedUser.LastUpdated}")
                    Log.d(TAG_ACTIVITY, "Rol: ${fetchedUser.Rol}")
                    return fetchedUser
                }
            }


        } catch (e: Exception) {
            Log.d(TAG_ACTIVITY, "Exception: ${e.message}")
        }

        Log.d(TAG_ACTIVITY, "USER NULL")
        return null
    }

    suspend fun getPlanInfo(email: String?): Plan? {
        try {
            val snapshot = this.usersRef
                .whereEqualTo("Email", email)
                .get()
                .await()
            if (snapshot.documents.isNotEmpty()) {
                val planSnapshot = snapshot.documents.first()
                val selectedPatient = planSnapshot.toObject(UserResponse::class.java)
                if (selectedPatient != null) {
                    val selectedPlan = selectedPatient.PlanAsignado?.PlanAlimentacion
                    if (selectedPlan != null) {
                        val snapshot2 = this.planRef.document(selectedPlan).get().await()
                        if (snapshot2.exists()) {
                            return snapshot2.toObject(Plan::class.java)
                        }
                    }
                    return null
                }
                return null
            }
        } catch (e: Exception) {
            Log.d(TAG_ACTIVITY, "Exception: ${e.message}")
        }
        return null
    }

}
