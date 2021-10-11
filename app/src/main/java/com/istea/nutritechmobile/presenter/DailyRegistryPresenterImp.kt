package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.storage.FirebaseStorage
import com.istea.nutritechmobile.data.DailyUploadRegistry
import com.istea.nutritechmobile.firebase.FirebaseStorageManager
import com.istea.nutritechmobile.helpers.CameraManager
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.model.interfaces.IDailyRegistryRepository
import com.istea.nutritechmobile.presenter.interfaces.IDailyRegistryPresenter
import com.istea.nutritechmobile.ui.interfaces.IDailyRegistryView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

const val TAG = "DailyRegistryPresenterImp"

class DailyRegistryPresenterImp(
    private val view: IDailyRegistryView,
    private val repo: IDailyRegistryRepository,
    private val storage: FirebaseStorageManager,
    private val camera: CameraManager
) :
    IDailyRegistryPresenter {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun addDailyRegistry(dailyUploadRegistry: DailyUploadRegistry, user: String) {

        try {
            GlobalScope.launch(Dispatchers.Main) {
                repo.addDailyUpload(dailyUploadRegistry, user)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val image = File(dailyUploadRegistry.UrlImage)
                            if (image.exists()){
                                val bytes = image.readBytes()
                                storage.uploadImgFood(bytes)
                                showSuccessAddMessage()
                            }
                        }
                    }
                    .addOnFailureListener {
                        showFailureAddMessage()
                    }
            }
        } catch (e: Exception) {

        }
    }

    private fun showFailureAddMessage() {
        UIManager.showMessageShort(
            view as Activity, "El Registro diario no se inserto correctamente, intente nuevamente"
        )
    }

    private fun showSuccessAddMessage() {
        UIManager.showMessageShort(
            view as Activity, "El Registro diario se insertar correctamente"
        )
    }
}