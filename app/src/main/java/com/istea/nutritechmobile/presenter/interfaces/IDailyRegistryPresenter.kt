package com.istea.nutritechmobile.presenter.interfaces

import com.google.android.gms.tasks.Task
import com.istea.nutritechmobile.data.DailyUploadRegistry

interface IDailyRegistryPresenter {
    fun addDailyRegistry(dailyUploadRegistry: DailyUploadRegistry, user: String)
}