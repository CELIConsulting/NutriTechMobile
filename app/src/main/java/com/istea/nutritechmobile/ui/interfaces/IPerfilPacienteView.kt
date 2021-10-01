package com.istea.nutritechmobile.ui.interfaces

import com.istea.nutritechmobile.data.UserResponse

interface IPerfilPacienteView {
    //Bindear al paciente con las views del form
    fun showPacienteInfo(paciente: UserResponse)
    //Crear un paciente con la info actualizada en base al form del perfil
    fun updatePacienteInfo()
    //Volver a la pantalla de login
    fun goBackToLogin()
}