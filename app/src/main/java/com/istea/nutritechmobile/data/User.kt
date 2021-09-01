package com.istea.nutritechmobile.data

import java.io.Serializable

data class User(
    val mail: String,
    val password: String,
) : Serializable

data class UserResponse(
    val id: Int? = null,
    val mail: String,
    val password: String,
    val nombre: String,
    val apellido: String,
    val rol: Role
) : Serializable {
    constructor() : this(null, "", "", "", "", Role(null, "", ""))
}






