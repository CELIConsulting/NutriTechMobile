package com.istea.nutritechmobile.presenter

import com.istea.nutritechmobile.model.interfaces.IPlanDisplayRepository
import com.istea.nutritechmobile.presenter.interfaces.IPlanDisplayPresenter
import com.istea.nutritechmobile.ui.interfaces.IPlanDisplayView


private const val TAG_ACTIVITY = "PlanDisplayPresenterImp"

class PlanDisplayPresenterImp(
    private val view: IPlanDisplayView,
    private val repo: IPlanDisplayRepository
) : IPlanDisplayPresenter {

    override suspend fun fillPlanInfo(email: String?) {
        val dataRepo = repo.getPlanData(email);
        view.fillDataView(dataRepo)
    }
}