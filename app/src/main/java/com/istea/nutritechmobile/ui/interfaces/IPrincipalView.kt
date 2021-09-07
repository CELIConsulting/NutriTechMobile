package com.istea.nutritechmobile.ui.interfaces

interface IPrincipalView {
    fun welcomeUser(name: String, lastName: String)
    fun generateQuoteOfTheDay(phrase: String, author: String)
    fun showInProgressMessage()
    fun goBackToLogin()
}