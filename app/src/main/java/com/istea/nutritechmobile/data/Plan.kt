package com.istea.nutritechmobile.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class Plan(
    val Id: Int?,
    val Nombre: String,
    val Tipo: String,
    val CantAguaDiaria: Float,
    val CantColacionesDiarias: Int,
    val Desayuno: List<String>,
    val Almuerzo: List<String>,
    val Merienda: List<String>,
    val Cena: List<String>,
    val Colacion: List<String>,
    val LastUpdated: Timestamp,
) : Serializable {
    constructor() : this(
        null,
        "",
        "",
        0f,
        0,
        listOf<String>(),
        listOf<String>(),
        listOf<String>(),
        listOf<String>(),
        listOf<String>(),
        Timestamp.now()
    )
}