package com.istea.nutritechmobile.presenter

import com.istea.nutritechmobile.model.interfaces.ICargaDiariaRepository
import com.istea.nutritechmobile.presenter.interfaces.IDailyRegistryPresenter
import com.istea.nutritechmobile.ui.interfaces.ICargaDiariaView


class DailyRegistryPresenterImp(
    private val view: ICargaDiariaView,
    private val repo: ICargaDiariaRepository,
) :
    IDailyRegistryPresenter {
}