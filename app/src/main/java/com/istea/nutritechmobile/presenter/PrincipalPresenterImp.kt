package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.util.Log
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.getTextFromResource
import com.istea.nutritechmobile.presenter.interfaces.IPrincipalPresenter
import com.istea.nutritechmobile.ui.activity.LOGGED_USER
import com.istea.nutritechmobile.ui.interfaces.IPrincipalView


//TODO: Falta agregar capa de repo para consumir API para las frases
//TODO: Guardar los datos del usuario logueado en el storage / Ver alguna clase Utility
//TODO: Se borraría la key pertinente al hacer logout


private const val TAG_ACTIVITY = "PrincipalPresenterImp"

class PrincipalPresenterImp(private val view: IPrincipalView) : IPrincipalPresenter {
    override fun loggedUserData() {
        try {

            val activity = view as Activity
            activity.intent.extras?.getParcelable<UserResponse>(LOGGED_USER)?.let {
                view.welcomeUser(it.Nombre, it.Apellido)
            }

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

