package com.istea.nutritechmobile.ui.interfaces

import com.istea.nutritechmobile.data.UserResponse

interface IPerfilPacienteView: IToolbar, IActivity {
    fun showPacienteInfo(paciente: UserResponse)
    fun updatePacienteInfo()
    fun isUserFirstLogin(firstTime: Boolean)
}