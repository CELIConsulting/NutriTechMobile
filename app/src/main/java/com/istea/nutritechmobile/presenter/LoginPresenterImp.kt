package com.istea.nutritechmobile.presenter

import android.app.Activity
import android.util.Log
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.helpers.getTextFromResource
import com.istea.nutritechmobile.helpers.mailFormatIsValid
import com.istea.nutritechmobile.model.interfaces.ILoginRepository
import com.istea.nutritechmobile.presenter.interfaces.ILoginPresenter
import com.istea.nutritechmobile.ui.interfaces.ILoginView

private const val TAG_ACTIVITY = "LoginPresenterImp"

class LoginPresenterImp(
    private val view: ILoginView,
    private val repo: ILoginRepository,
) :
    ILoginPresenter {
    override suspend fun doLogin(mail: String, password: String) {

        if (isLoginInputValid(mail, password)) {
            val user = User(mail, password)

            if (repo.checkUserData(user)) {
                //TODO: Debe avanzar a la pantalla principal
            } else
                view.showMessage(
                    getTextFromResource(
                        view as Activity,
                        R.string.user_not_found
                    )
                )
        }
    }

    private fun isLoginInputValid(mail: String, password: String): Boolean {

        if (!emptyImputs(mail, password)) {
            return if (mailFormatIsValid(mail)) {
                true
            } else {
                view.showMessage(
                    getTextFromResource(
                        view as Activity,
                        R.string.mail_format_invalid
                    )
                )
                false
            }
        }

        return false
    }

    private fun emptyImputs(mail: String, pass: String): Boolean {
        val mailEmpty = mail.isEmpty()
        val passEmpty = pass.isEmpty()

        if (mailEmpty && passEmpty) {
            view.showMessage(
                getTextFromResource(
                    view as Activity, R.string.both_empty
                )
            )
            return true
        } else if (mailEmpty && !passEmpty) {
            view.showMessage(
                getTextFromResource(
                    view as Activity,
                    R.string.mail_empty
                )
            )
            return true
        } else if (!mailEmpty && passEmpty) {
            view.showMessage(
                getTextFromResource(
                    view as Activity,
                    R.string.pass_empty
                )
            )
            return true
        }

        return false
    }

}

