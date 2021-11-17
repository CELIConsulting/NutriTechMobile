package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.istea.nutritechmobile.data.RegistroCorporal
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.firebase.FirebaseStorageManager
import com.istea.nutritechmobile.helpers.CameraManager
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.model.interfaces.IRegistroCorporalRepository
import com.istea.nutritechmobile.presenter.interfaces.IRegistroCorporalPresenter
import com.istea.nutritechmobile.ui.interfaces.IRegistroCorporalView
import kotlinx.coroutines.*
import java.io.File
import com.istea.nutritechmobile.helpers.images.BitmapHelper


private const val TAG_ACTIVITY = "RegistroCorporalPresenterImp"

class RegistroCorporalPresenterImp(
    private val view: IRegistroCorporalView,
    private val repo: IRegistroCorporalRepository,
    private val storage: FirebaseStorageManager,
    private val camera: CameraManager
) : IRegistroCorporalPresenter {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun addCorporalRegistry(user: String, registro: RegistroCorporal) {
        try {
            GlobalScope.launch(Dispatchers.Main) {
                repo.addCorporalRegistry(user, registro)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val image = File(registro.UrlImage)
                            if (image.exists()) {
                                val stream =
                                    BitmapHelper.reduceImageSizeToUpload(view as Activity, image)
                                GlobalScope.launch(Dispatchers.IO) {
                                    storage.uploadImgBody(stream, registro.ImageName)
                                }

                                showSuccessAddMessage()
                                view.refreshActivity()
                            }
                        }
                    }
                    .addOnFailureListener {
                        showFailureAddMessage()
                    }
            }
        } catch (e: Exception) {
            Log.d(TAG_ACTIVITY, e.message ?: "Something")
        }
    }

    override suspend fun updatePaciente(paciente: UserResponse?, isFirstLogin: Boolean) {
        try {
            GlobalScope.launch(Dispatchers.Main) {
                if (paciente != null) {
                    repo.updatePacienteInfo(paciente)
                        .addOnCompleteListener {
                            GlobalScope.launch(Dispatchers.Main) {
                                val updateSessionManagerOK = withContext(Dispatchers.IO) {
                                    repo.updateLoggedUser(paciente)
                                }

                                if (updateSessionManagerOK) {
                                    if (isFirstLogin) {
                                        showSuccessAddMessage()
                                    } else {
                                        showSuccessUpdateMessage()
                                    }
                                } else {
                                    Log.d(
                                        TAG_ACTIVITY,
                                        "No se pudo actualizar el usuario logueado en el Session Manager"
                                    )
                                }

                            }
                        }
                        .addOnFailureListener {
                            finishSession()

                        }
                } else {
                    Log.d(TAG_ACTIVITY, "No se pudo actualizar el paciente porque es nulo")
                    finishSession()
                }
            }
        } catch (e: Exception) {
            Log.d(TAG_ACTIVITY, e.message ?: "Something")
        }

    }

    private fun finishSession() {
        GlobalScope.launch(Dispatchers.IO) {
            repo.logoutUser()
            delay(1000)
        }

        view.goToLoginView()
    }

    override suspend fun getLoggedUser(): UserResponse? {
        return repo.getLoggedUser()
    }


    private fun showFailureAddMessage() {
        UIManager.showMessageShort(
            view as Activity, "Su datos no pudieron ser cargados, intente nuevamente"
        )
    }

    private fun showSuccessAddMessage() {
        UIManager.showMessageShort(
            view as Activity, "Sus datos han sido cargados correctamente"
        )
        view.goToHomeView()
    }

    private fun showSuccessUpdateMessage() {
        UIManager.showMessageShort(
            view as Activity, "Sus datos han sido actualizados correctamente"
        )
        view.goToProfileView()
    }
}