package com.istea.nutritechmobile.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class PlanAsignacion(
    val PlanAlimentacion: String,
    val NotasAdicionales: String,
    val LastAssignment: Timestamp,
):Serializable {
    constructor(): this("","",Timestamp.now())
}