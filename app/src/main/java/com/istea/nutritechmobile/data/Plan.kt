package com.istea.nutritechmobile.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
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
    )
}