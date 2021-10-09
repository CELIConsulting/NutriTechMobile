package com.istea.nutritechmobile.model

import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.model.interfaces.ILoginRepository

private const val TAG_ACTIVITY = "LoginRepositoryImp"

class LoginRepositoryImp(
    private val firebaseFirestoreManager: FirebaseFirestoreManager,
) : ILoginRepository {
    override suspend fun checkUserData(user: User): UserResponse? {
        return firebaseFirestoreManager.getUserWithCredentials(user)
    }

}