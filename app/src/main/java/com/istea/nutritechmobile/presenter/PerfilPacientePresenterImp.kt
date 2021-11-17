package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.util.Log
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.model.interfaces.IPerfilPacienteRepository
import com.istea.nutritechmobile.presenter.interfaces.IPerfilPacientePresenter
import com.istea.nutritechmobile.ui.interfaces.IPerfilPacienteView
import kotlinx.coroutines.*

private const val TAG_ACTIVITY = "PerfilPacientePresenterImp"

class PerfilPacientePresenterImp(
    private val view: IPerfilPacienteView,
    private val repo: IPerfilPacienteRepository
) : IPerfilPacientePresenter {

    override suspend fun getPaciente() {
        GlobalScope.launch(Dispatchers.Main) {

            try {
                val loggedUser = withContext(Dispatchers.Main) {
                    repo.getLoggedUser()
                }

                if (loggedUser != null) {
                    if (isPatientFirstLogin(loggedUser)) {
                        view.isUserFirstLogin(true)
                    } else {
                        view.isUserFirstLogin(false)
                    }

                    view.showPacienteInfo(loggedUser)

                } else {
                    finishSession()
                }

            } catch (exception: Exception) {
                Log.d(TAG_ACTIVITY, "Cannot get patient because: ${exception.message}")
                finishSession()
            }
        }
    }

    override suspend fun updatePaciente(paciente: UserResponse) {
        val loggedUser = withContext(Dispatchers.Main) {
            repo.getLoggedUser()
        }

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
                Log.i(TAG_ACTIVITY, "Cannot update patient because ${e.message}")
                finishSession()
            }

        } else {
            finishSession()
        }
    }

    override fun isPatientFirstLogin(paciente: UserResponse): Boolean {
        return !paciente.TyC
    }

    private fun showSuccessUpdateMessage() {
        UIManager.showMessageShort(
            view as Activity, "El perfil ha sido actualizado"
        )
        view.goToHomeView()
    }

    private fun showFailureUpdateMessage() {
        UIManager.showMessageShort(
            view as Activity, "El perfil no ha podido ser actualizado, intente nuevamente"
        )
    }

    private suspend fun finishSession() {
        repo.logoutUser()
        view.goToLoginView()
    }

}