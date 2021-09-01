package com.istea.nutritechmobile.model.interfaces

import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.io.FireStoreHelper

//TODO: Agregar clase de firestore
class LoginRepositoryImp(
    private val firestoreHelper: FireStoreHelper,
) : ILoginRepository {

    override fun checkUserExistence(user: User, success: () -> Unit, error: () -> Unit) {

    }
}