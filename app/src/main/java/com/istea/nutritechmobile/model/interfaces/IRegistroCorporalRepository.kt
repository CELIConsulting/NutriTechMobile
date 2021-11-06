package com.istea.nutritechmobile.model.interfaces

import com.google.android.gms.tasks.Task
import com.istea.nutritechmobile.data.RegistroCorporal

interface IRegistroCorporalRepository {
    suspend fun addCorporalRegistry(user: String, registro: RegistroCorporal): Task<Void>
}