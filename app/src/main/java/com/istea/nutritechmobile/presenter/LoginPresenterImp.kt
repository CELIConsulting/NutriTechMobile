package com.istea.nutritechmobile.presenter

import android.app.Activity
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.helpers.getTextFromResource
import com.istea.nutritechmobile.helpers.mailFormatIsValid
import com.istea.nutritechmobile.model.interfaces.ILoginRepository
import com.istea.nutritechmobile.presenter.interfaces.ILoginPresenter
import com.istea.nutritechmobile.ui.interfaces.ILoginView

class LoginPresenterImp(
    private val view: ILoginView,
    private val repo: ILoginRepository
) :
    ILoginPresenter {
    override fun doLogin(mail: String, password: String) {

        if (isLoginInputValid(mail, password)) {
            //GENERO USUARIO
            val user = User(mail, password)

            //REPO CONSULTA AL SERVICIO DE FIRESTORE Y AGUARDA RESPUESTA
            repo.checkUserExistence(user, {
                //OK
                view.goToNextScreen(user)
            }, {
                //ERROR
                view.showMessage(
                    getTextFromResource(
                        view as Activity,
                        R.string.user_not_found
                    )
                )
            })
        }
    }

    private fun isLoginInputValid(mail: String, password: String): Boolean {
        if (!emptyImputs(mail, password)) {
            if (mailFormatIsValid(mail)) {
                view.showMessage("DATOS VALIDOS PARA SER PROCESADOS")
                return true
            } else
                view.showMessage(
                    getTextFromResource(
                        view as Activity,
                        R.string.mail_format_invalid
                    )
                )
        }
        return false
    }


    //TODO: Revisar función de empty inputs
    private fun emptyImputs(mail: String, pass: String): Boolean {
        val mailEmpty = mail.isEmpty()
        val passEmpty = pass.isEmpty()

        if (mailEmpty && passEmpty) {
            view.showMessage(getTextFromResource(view as Activity, R.string.both_empty))
            return true
        } else {
            when {
                passEmpty -> view.showMessage(
                    getTextFromResource(
                        view as Activity,
                        R.string.pass_empty
                    )
                )
                mailEmpty -> view.showMessage(
                    getTextFromResource(
                        view as Activity,
                        R.string.mail_empty
                    )
                )
                else -> return true
            }
        }
        return false
    }

}