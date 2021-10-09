package com.istea.nutritechmobile.model

import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.model.interfaces.ICargaDiariaRepository

class CargaDiariaRepositoryImp(
    private val firebaseFirestoreManager: FirebaseFirestoreManager
): ICargaDiariaRepository {
}