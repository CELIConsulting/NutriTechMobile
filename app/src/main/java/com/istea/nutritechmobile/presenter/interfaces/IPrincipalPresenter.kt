package com.istea.nutritechmobile.presenter.interfaces

interface IPrincipalPresenter {
    suspend fun loggedUserData()
    fun getQuoteOfTheDay()
}