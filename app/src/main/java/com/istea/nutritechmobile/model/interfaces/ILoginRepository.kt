package com.istea.nutritechmobile.model.interfaces

import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.data.UserResponse

interface ILoginRepository {
    suspend fun checkUserData(user: User):UserResponse?
}