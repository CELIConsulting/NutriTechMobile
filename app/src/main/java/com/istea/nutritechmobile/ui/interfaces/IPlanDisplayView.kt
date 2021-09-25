package com.istea.nutritechmobile.ui.interfaces

import com.istea.nutritechmobile.data.Plan

interface IPlanDisplayView {
    suspend fun fillDataView(dataRepo: Plan?)
}