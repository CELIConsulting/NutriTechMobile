package com.istea.nutritechmobile.model

import com.istea.nutritechmobile.firebase.FireStoreHelper
import com.istea.nutritechmobile.model.interfaces.ICargaDiariaRepository

class CargaDiariaRepositoryImp(
    private val firestoreHelper: FireStoreHelper
): ICargaDiariaRepository {
}