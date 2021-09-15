package com.istea.nutritechmobile.model.interfaces

import com.istea.nutritechmobile.data.Plan

interface IPlanDisplayRepository {
    suspend fun getPlanData(user: String?): Plan?
}