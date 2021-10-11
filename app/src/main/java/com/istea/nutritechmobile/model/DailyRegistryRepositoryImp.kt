package com.istea.nutritechmobile.model

import com.google.android.gms.tasks.Task
import com.istea.nutritechmobile.data.DailyUploadRegistry
import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.model.interfaces.IDailyRegistryRepository

class DailyRegistryRepositoryImp(
    private val firebaseFirestoreManager: FirebaseFirestoreManager
): IDailyRegistryRepository {

    override suspend fun addDailyUpload(dailyUploadRegistry: DailyUploadRegistry, user: String): Task<Void> {
        return firebaseFirestoreManager.addDailyUpload(dailyUploadRegistry, user)
    }
}