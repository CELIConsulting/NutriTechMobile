package com.istea.nutritechmobile.presenter.interfaces

interface ILoginPresenter {
    suspend fun doLogin(mail: String, password: String)
}