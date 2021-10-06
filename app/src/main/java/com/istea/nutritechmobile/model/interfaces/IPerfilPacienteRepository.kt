package com.istea.nutritechmobile.model.interfaces

import com.istea.nutritechmobile.data.UserResponse

interface IPerfilPacienteRepository {
    suspend fun getCurrentUser(): UserResponse?
}