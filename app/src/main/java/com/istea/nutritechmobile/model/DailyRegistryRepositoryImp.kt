package com.istea.nutritechmobile.model

import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.model.interfaces.IDailyRegistryRepository

class DailyRegistryRepositoryImp(
    private val firebaseFirestoreManager: FirebaseFirestoreManager
): IDailyRegistryRepository {
}