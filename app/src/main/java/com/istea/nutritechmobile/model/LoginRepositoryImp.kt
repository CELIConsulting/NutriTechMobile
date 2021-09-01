package com.istea.nutritechmobile.model

import android.util.Log
import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.io.FireStoreHelper
import com.istea.nutritechmobile.model.interfaces.ILoginRepository

private const val TAG_ACTIVITY = "LoginRepositoryImp"

class LoginRepositoryImp(
    private val firestoreHelper: FireStoreHelper,
) : ILoginRepository {
    override suspend fun checkUserData(user: User): UserResponse? {
        return firestoreHelper.getUserWithCredentials(user)
    }

}