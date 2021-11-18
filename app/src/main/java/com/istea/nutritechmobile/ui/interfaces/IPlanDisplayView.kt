package com.istea.nutritechmobile.ui.interfaces

import com.istea.nutritechmobile.data.Plan

interface IPlanDisplayView: IToolbar {
    suspend fun fillDataView(dataRepo: Plan?)
    fun setupUI()
}