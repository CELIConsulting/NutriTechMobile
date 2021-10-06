package com.istea.nutritechmobile.model

import com.istea.nutritechmobile.io.FireStoreHelper
import com.istea.nutritechmobile.model.interfaces.ICargaDiariaRepository
import com.istea.nutritechmobile.model.interfaces.ILoginRepository

class CargaDiariaRepositoryImp(
    private val firestoreHelper: FireStoreHelper
): ICargaDiariaRepository {
}