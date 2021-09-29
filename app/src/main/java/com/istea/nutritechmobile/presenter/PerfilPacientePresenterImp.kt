package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.view.View
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.helpers.preferences.SessionManager
import com.istea.nutritechmobile.presenter.interfaces.IPerfilPacientePresenter
import com.istea.nutritechmobile.ui.interfaces.IPerfilPacienteView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PerfilPacientePresenterImp(private val view: IPerfilPacienteView) : IPerfilPacientePresenter {
    override suspend fun getPaciente() {
        //TODO: El repo deberia traerme esta data del SessionManager
        val loggedUser: UserResponse? = withContext(Dispatchers.IO) {
            SessionManager.getLoggedUser()
        }

        if (loggedUser != null) {
            view.showPacienteInfo(loggedUser)
        } else {
            view.goBackToLogin()
        }
    }

    override suspend fun updatePaciente(paciente: UserResponse) {
        UIManager.showMessageShort(view as Activity, "Funcion en desarrollo" )
    }
}