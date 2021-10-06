package com.istea.nutritechmobile.model

import com.google.android.gms.tasks.Task
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.preferences.SessionManager
import com.istea.nutritechmobile.io.FireStoreHelper
import com.istea.nutritechmobile.model.interfaces.IPerfilPacienteRepository
import kotlinx.coroutines.*


class PerfilPacienteRepositoryImp(private val fireStoreHelper: FireStoreHelper) :
    IPerfilPacienteRepository {

    override suspend fun getLoggedUser(): UserResponse? =
        withContext(Dispatchers.IO) {
            SessionManager.getLoggedUser()
        }

    override suspend fun updateLoggedUser(user: UserResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            SessionManager.saveLoggedUser(user)
        }
    }

    override suspend fun logoutUser() {
        GlobalScope.launch(Dispatchers.IO) {
            SessionManager.saveLoggedUser(null)
        }
    }

    override suspend fun updatePatient(profileData: UserResponse): Task<Void> {
        return fireStoreHelper.updatePatientProfile(profileData)
    }

}

