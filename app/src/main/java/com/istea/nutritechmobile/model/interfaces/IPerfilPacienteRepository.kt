package com.istea.nutritechmobile.model.interfaces

import com.google.android.gms.tasks.Task
import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.data.UserResponse

interface IPerfilPacienteRepository {
    suspend fun getLoggedUser():UserResponse?
    suspend fun updateLoggedUser(user: UserResponse)
    suspend fun updatePatient(profileData: UserResponse): Task<Void>
    suspend fun logoutUser()
}