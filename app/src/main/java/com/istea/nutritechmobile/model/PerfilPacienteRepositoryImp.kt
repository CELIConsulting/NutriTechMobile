package com.istea.nutritechmobile.model

import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.model.interfaces.IPerfilPacienteRepository

class PerfilPacienteRepositoryImp: IPerfilPacienteRepository {
    override suspend fun getCurrentUser(): UserResponse? {
        TODO("Not yet implemented")
    }

}