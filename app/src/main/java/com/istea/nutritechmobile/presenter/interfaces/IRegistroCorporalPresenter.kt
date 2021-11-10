package com.istea.nutritechmobile.presenter.interfaces

import com.istea.nutritechmobile.data.RegistroCorporal
import com.istea.nutritechmobile.data.UserResponse

interface IRegistroCorporalPresenter {
    fun addCorporalRegistry(user: String, registro: RegistroCorporal)
    fun updatePaciente(paciente: UserResponse)
}