package com.istea.nutritechmobile.presenter.interfaces

import com.istea.nutritechmobile.data.UserResponse

interface IPerfilPacientePresenter {
    suspend fun getPaciente()
    suspend fun updatePaciente(paciente: UserResponse)
}