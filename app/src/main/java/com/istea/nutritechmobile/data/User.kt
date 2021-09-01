package com.istea.nutritechmobile.data

import java.io.Serializable

data class User(
    val id: Int? = null,
    val mail: String,
    val password: String,
    val nombre: String,
    val apellido: String,
    val rol: Role
) : Serializable {

    constructor(mail: String, password: String) : this(
        null,
        mail,
        password,
        "",
        "",
        Role(null, "", "")
    )

}


