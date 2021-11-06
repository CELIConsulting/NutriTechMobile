package com.istea.nutritechmobile.presenter.interfaces

import com.istea.nutritechmobile.data.RegistroCorporal

interface IRegistroCorporalPresenter {
    fun addCorporalRegistry(user: String, registro: RegistroCorporal)
}