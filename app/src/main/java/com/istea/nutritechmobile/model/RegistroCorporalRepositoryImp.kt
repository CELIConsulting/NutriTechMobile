package com.istea.nutritechmobile.model

import com.google.android.gms.tasks.Task
import com.istea.nutritechmobile.data.RegistroCorporal
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.model.interfaces.IRegistroCorporalRepository

class RegistroCorporalRepositoryImp(private val firestoreManager: FirebaseFirestoreManager) :
    IRegistroCorporalRepository {
    override suspend fun addCorporalRegistry(user: String, registro: RegistroCorporal): Task<Void> {
        return firestoreManager.addBodyProgress(user, registro)
    }

    override suspend fun updatePacienteCorporal(paciente: UserResponse): Task<Void> {
        return firestoreManager.updatePatientProfile(paciente)
    }
}