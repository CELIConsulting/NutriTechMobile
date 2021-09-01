package com.istea.nutritechmobile.data

import java.io.Serializable

data class Role(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String,
) : Serializable

