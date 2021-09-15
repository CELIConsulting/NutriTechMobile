package com.istea.nutritechmobile.model

import com.istea.nutritechmobile.data.Plan
import com.istea.nutritechmobile.io.FireStoreHelper
import com.istea.nutritechmobile.model.interfaces.IPlanDisplayRepository

private const val TAG_ACTIVITY = "PlanDisplayRepositoryImp"

class PlanDisplayRepositoryImp(private val firestoreHelper: FireStoreHelper): IPlanDisplayRepository {

    override suspend fun getPlanData(user: String?): Plan? {
        return firestoreHelper.getPlanInfo(user)
    }
}