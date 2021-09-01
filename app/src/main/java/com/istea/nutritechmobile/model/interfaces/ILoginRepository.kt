package com.istea.nutritechmobile.model.interfaces

import com.istea.nutritechmobile.data.User

interface ILoginRepository {
    fun checkUserExistence(user: User, success: () -> Unit, error: () -> Unit)
}