package com.istea.nutritechmobile.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class User(
    val mail: String,
    val password: String,
) : Serializable

data class UserResponse(
    val Nombre: String,
    val Apellido: String,
    val Email: String,
    val Password: String,
    val Rol: String,
    val FechaNacimiento: Timestamp?,
    val Telefono: String?,
    val LastUpdated: Timestamp?,
    val Altura: Float?,
    val Peso: Float?,
    val MedidaCintura: Float?,
    val TipoAlimentacion: String?,
    val PlanAsignado: PlanAsignacion?,
) : Serializable {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        Timestamp.now(),
        "",
        Timestamp.now(),
        0f,
        0f,
        0f,
        "",
        PlanAsignacion(
            "",
            "",
            Timestamp.now()
        )
    )
}
