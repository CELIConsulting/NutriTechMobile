package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.util.Log
import android.view.View
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.helpers.preferences.SessionManager
import com.istea.nutritechmobile.model.interfaces.IPerfilPacienteRepository
import com.istea.nutritechmobile.presenter.interfaces.IPerfilPacientePresenter
import com.istea.nutritechmobile.ui.interfaces.IPerfilPacienteView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG_ACTIVITY = "PerfilPacientePresenterImp"

class PerfilPacientePresenterImp(
    private val view: IPerfilPacienteView,
    private val repo: IPerfilPacienteRepository
) : IPerfilPacientePresenter {

    override suspend fun getPaciente() {
        try {
            val loggedUser = repo.getLoggedUser()

            if (loggedUser != null) {
                view.showPacienteInfo(loggedUser)
            } else {
                finishSession()
            }

        } catch (exception: Exception) {
            Log.d(TAG_ACTIVITY, "Cannot get patient because: ${exception.message}")
            finishSession()
        }
    }

    override suspend fun updatePaciente(paciente: UserResponse) {
        val loggedUser = repo.getLoggedUser()

        if (loggedUser != null) {

            try {
                GlobalScope.launch(Dispatchers.Main)
                {
                    repo.updatePatient(paciente)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                GlobalScope.launch(Dispatchers.IO)
                                {
                                    repo.updateLoggedUser(paciente)
                                }

                                showSuccessUpdateMessage()
                            }
                        }
                        .addOnFailureListener {
                            showFailureUpdateMessage()
                        }
                }
            } catch (e: Exception) {
                Log.d(TAG_ACTIVITY, "Cannot update patient because ${e.message}")
                finishSession()
            }

        } else {
            finishSession()
        }
    }

    private fun showSuccessUpdateMessage() {
        UIManager.showMessageShort(
            view as Activity, "El perfil ha sido actualizado"
        )
        view.goBackToMain()
    }

    private fun showFailureUpdateMessage() {
        UIManager.showMessageShort(
            view as Activity, "El perfil no ha podido ser actualizado, intente nuevamente"
        )
    }

    private suspend fun finishSession() {
        repo.logoutUser()
        view.goBackToLogin()
    }

}