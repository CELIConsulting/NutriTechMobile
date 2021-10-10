package com.istea.nutritechmobile.presenter

import com.istea.nutritechmobile.model.interfaces.IDailyRegistryRepository
import com.istea.nutritechmobile.presenter.interfaces.IDailyRegistryPresenter
import com.istea.nutritechmobile.ui.interfaces.ICargaDiariaView


class DailyRegistryPresenterImp(
    private val view: ICargaDiariaView,
    private val repo: IDailyRegistryRepository,
) :
    IDailyRegistryPresenter {
}