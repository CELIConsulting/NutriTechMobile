package com.istea.nutritechmobile.data

import com.google.firebase.Timestamp
import com.google.type.DateTime
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

data class User(
    val mail: String,
    val password: String,
) : Serializable

data class UserResponse(
    val Id: Int? = null,
    val Email: String,
    val Password: String,
    val Nombre: String,
    val Apellido: String,
    val LastUpdated: Timestamp,
    val Rol: Role
) : Serializable {
    constructor() : this(null, "", "", "", "", Timestamp.now(), Role())
}






