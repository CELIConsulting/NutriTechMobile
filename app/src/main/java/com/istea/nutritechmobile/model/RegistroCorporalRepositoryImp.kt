package com.istea.nutritechmobile.model

import com.google.android.gms.tasks.Task
import com.istea.nutritechmobile.data.RegistroCorporal
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.helpers.preferences.SessionManager
import com.istea.nutritechmobile.model.interfaces.IRegistroCorporalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroCorporalRepositoryImp(private val firestoreManager: FirebaseFirestoreManager) :
    IRegistroCorporalRepository {
    override suspend fun addCorporalRegistry(user: String, registro: RegistroCorporal): Task<Void> {
        return firestoreManager.addBodyProgress(user, registro)
    }

    override suspend fun updatePacienteInfo(paciente: UserResponse): Task<Void> {
        return firestoreManager.updatePatientProfile(paciente)
    }

    override suspend fun getLoggedUser(): UserResponse? = SessionManager.getLoggedUser()

    override suspend fun updateLoggedUser(user: UserResponse): Boolean{
        return SessionManager.saveLoggedUser(user)
    }

    override suspend fun logoutUser() {
        SessionManager.saveLoggedUser(null)
    }
}