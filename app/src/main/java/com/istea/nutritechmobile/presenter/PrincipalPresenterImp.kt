package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.util.Log
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.getTextFromResource
import com.istea.nutritechmobile.helpers.preferences.SessionManager
import com.istea.nutritechmobile.presenter.interfaces.IPrincipalPresenter
import com.istea.nutritechmobile.ui.activity.LOGGED_USER
import com.istea.nutritechmobile.ui.interfaces.IPrincipalView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//TODO: Falta agregar capa de repo para consumir API para las frases

private const val TAG_ACTIVITY = "PrincipalPresenterImp"

class PrincipalPresenterImp(private val view: IPrincipalView) : IPrincipalPresenter {
    override suspend fun loggedUserData() {
        try {
            val loggedUser: UserResponse? = withContext(Dispatchers.IO) {
                SessionManager.getLoggedUser()
            }

            if (loggedUser != null) {
                Log.i(TAG_ACTIVITY, "Usuario no nulo")
                view.welcomeUser(loggedUser.Nombre, loggedUser.Apellido)
            } else {
                Log.i(TAG_ACTIVITY, "Usuario nulo")
                view.goBackToLogin()
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

