package com.istea.nutritechmobile.data

import java.io.Serializable

data class Role(
    val Id: Int? = null,
    val Nombre: String,
    val Descripcion: String,
) : Serializable {
    constructor() : this(null, "", "")
}

