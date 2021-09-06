package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.util.Log
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.helpers.getTextFromResource
import com.istea.nutritechmobile.presenter.interfaces.IPrincipalPresenter
import com.istea.nutritechmobile.ui.interfaces.IPrincipalView

//TODO: Falta agregar capa de repo para consumir API para las frases

private const val TAG_ACTIVITY = "PrincipalPresenterImp"

class PrincipalPresenterImp(private val view: IPrincipalView) : IPrincipalPresenter {
    override fun loggedUserData() {
        try {
            val activity = view as Activity
            val nombre = activity.intent.extras?.getString("Nombre") ?: "User"
            val apellido = activity.intent.extras?.getString("Apellido") ?: "Not found"
            Log.d(TAG_ACTIVITY, "Nombre: $nombre | Apellido: $apellido")
            view.welcomeUser(nombre, apellido)

        } catch (exception: Exception) {
            Log.d(TAG_ACTIVITY, "Exception: ${exception.message}")
            view.goBackToLogin()
        }
    }

    override fun getQuoteOfTheDay() {
        val frase = getTextFromResource(view as Activity, R.string.daily_phrase_test)
        val autor = getTextFromResource(view as Activity, R.string.daily_author_test)
        view.generateQuoteOfTheDay(frase, autor)
    }
}

