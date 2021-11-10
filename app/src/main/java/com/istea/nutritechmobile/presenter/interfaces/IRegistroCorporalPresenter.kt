package com.istea.nutritechmobile.presenter.interfaces

import com.istea.nutritechmobile.data.RegistroCorporal
import com.istea.nutritechmobile.data.UserResponse

interface IRegistroCorporalPresenter {
    fun addCorporalRegistry(user: String, registro: RegistroCorporal)
    suspend fun updatePaciente(paciente: UserResponse?)
    suspend fun getLoggedUser(): UserResponse?
}