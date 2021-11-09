package com.istea.nutritechmobile.firebase

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.istea.nutritechmobile.data.*
import com.istea.nutritechmobile.helpers.*
import kotlinx.coroutines.tasks.await


private const val TAG = "FirebaseFirestoreManager"

// FIXME: Organizar esta clase para que todo lo que tenga que ver con
// se realice aca
class FirebaseFirestoreManager(context: Context) {

    private var db = FirebaseFirestore.getInstance()
    private val settings = firestoreSettings {
        isPersistenceEnabled = true
    }

    //Collections
    private val usersRef = this.db.collection("Users")
    private val planRef = this.db.collection("Planes")
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

            // Generar user a partir del snapshot
            if (snapshot.documents.isNotEmpty()) {
                val userSnapshot = snapshot.documents.first()
                val fetchedUser = userSnapshot.toObject(UserResponse::class.java)

                if (fetchedUser != null) {
                    Log.d(TAG, "Nombre: ${fetchedUser.Nombre}")
                    Log.d(TAG, "Apellido: ${fetchedUser.Apellido}")
                    Log.d(TAG, "Mail: ${fetchedUser.Email}")
                    Log.d(TAG, "Password: ${fetchedUser.Password}")
                    Log.d(TAG, "Timestamp: ${fetchedUser.LastUpdated}")
                    Log.d(TAG, "Rol: ${fetchedUser.Rol}")
                    return fetchedUser
                }
            }


        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
        }

        Log.d(TAG, "USER NULL")
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
            Log.d(TAG, "Exception: ${e.message}")
        }
        return null
    }

    fun updatePatientProfile(user: UserResponse): Task<Void> {
        //Por el momento, definir unicamente los campos que deben ser modificados
        val modifiedFields = hashMapOf<String, Any?>()
        modifiedFields[CAMPO_ALTURA] = user.Altura
        modifiedFields[CAMPO_PESO] = user.Peso
        modifiedFields[CAMPO_TELEFONO] = user.Telefono
        modifiedFields[CAMPO_MEDIDA] = user.MedidaCintura
        modifiedFields[CAMPO_TIPOALIMENTACION] = user.TipoAlimentacion

        return usersRef
            .document(user.Email)
            .update(modifiedFields)

    }

    fun addDailyUpload(user: String, dailyUploadRegistry: DailyUploadRegistry): Task<Void> {
        return usersRef.document(user).collection(DAILYUPLOAD)
            .document(dailyUploadRegistry.ImageName).set(dailyUploadRegistry)
    }

    fun addBodyProgress(user: String, bodyProgress: RegistroCorporal): Task<Void> {
        return usersRef.document(user).collection(BODYUPLOAD)
            .document(bodyProgress.ImageName).set(bodyProgress)
    }


}