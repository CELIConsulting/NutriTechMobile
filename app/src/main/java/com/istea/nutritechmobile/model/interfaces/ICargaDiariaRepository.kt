package com.istea.nutritechmobile.model.interfaces

import com.google.android.gms.tasks.Task
import com.istea.nutritechmobile.data.DailyUploadRegistry

interface IDailyRegistryRepository {
    suspend fun addDailyUpload(dailyUploadRegistry: DailyUploadRegistry, user: String): Task<Void>
}