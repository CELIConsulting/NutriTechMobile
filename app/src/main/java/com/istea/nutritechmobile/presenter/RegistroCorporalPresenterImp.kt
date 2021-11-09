package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.istea.nutritechmobile.data.RegistroCorporal
import com.istea.nutritechmobile.firebase.FirebaseStorageManager
import com.istea.nutritechmobile.helpers.CameraManager
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.model.interfaces.IRegistroCorporalRepository
import com.istea.nutritechmobile.presenter.interfaces.IRegistroCorporalPresenter
import com.istea.nutritechmobile.ui.interfaces.IRegistroCorporalView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

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
                                val stream = image.readBytes()
                                GlobalScope.launch(Dispatchers.IO) {
                                    storage.uploadImgBody(stream)
                                }

                                showSuccessAddMessage()
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

    private fun showFailureAddMessage() {
        UIManager.showMessageShort(
            view as Activity, "El Registro corporal no se inserto correctamente, intente nuevamente"
        )
    }

    private fun showSuccessAddMessage() {
        UIManager.showMessageShort(
            view as Activity, "El Registro corporal se pudo insertar correctamente"
        )
    }
}