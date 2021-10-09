package com.istea.nutritechmobile.model

import com.istea.nutritechmobile.data.Plan
import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.model.interfaces.IPlanDisplayRepository

private const val TAG_ACTIVITY = "PlanDisplayRepositoryImp"

class PlanDisplayRepositoryImp(private val firebaseFirestoreManager: FirebaseFirestoreManager): IPlanDisplayRepository {

    override suspend fun getPlanData(user: String?): Plan? {
        return firebaseFirestoreManager.getPlanInfo(user)
    }
}