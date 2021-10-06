package com.istea.nutritechmobile.ui.interfaces

import com.istea.nutritechmobile.data.UserResponse

interface IPerfilPacienteView {
    fun showPacienteInfo(paciente: UserResponse)
    fun updatePacienteInfo()
    fun goBackToLogin()
    fun goBackToMain()
}