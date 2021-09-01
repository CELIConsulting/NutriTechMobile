package com.istea.nutritechmobile.model.interfaces

import com.istea.nutritechmobile.data.User

interface ILoginRepository {
    suspend fun checkUserData(user: User):Boolean
}