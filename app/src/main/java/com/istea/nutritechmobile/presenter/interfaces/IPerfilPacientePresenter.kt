package com.istea.nutritechmobile.presenter.interfaces

import com.istea.nutritechmobile.data.UserResponse

interface IPerfilPacientePresenter {
    //Obtener info del paciente logueado en base a lo guardado por el SessionManager
    suspend fun getPaciente()
    //Actualizar info del paciente en la base de datos y en el SessionManager
    suspend fun updatePaciente(paciente: UserResponse)
}