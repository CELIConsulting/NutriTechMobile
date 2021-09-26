package com.istea.nutritechmobile.presenter

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.helpers.getTextFromResource
import com.istea.nutritechmobile.helpers.mailFormatIsValid
import com.istea.nutritechmobile.model.interfaces.ILoginRepository
import com.istea.nutritechmobile.presenter.interfaces.ILoginPresenter
import com.istea.nutritechmobile.ui.interfaces.ILoginView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val TAG_ACTIVITY = "LoginPresenterImp"

class LoginPresenterImp(
    private val view: ILoginView,
    private val repo: ILoginRepository,
    private val auth: FirebaseAuth,
) :
    ILoginPresenter {
    override suspend fun doLogin(mail: String, password: String) {

        if (isLoginInputValid(mail, password)) {

            auth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        GlobalScope.launch(Dispatchers.IO) {
                            withContext(Dispatchers.Main) {
                                val user = User(mail, password)
                                var userResponse = repo.checkUserData(user)
                                if (userResponse != null) {
                                    view.goToNextScreen(userResponse)
                                } else {
                                    view.showMessage(
                                        getTextFromResource(
                                            view as Activity,
                                            R.string.user_not_found
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener { _ ->
                    view.showMessage(
                        getTextFromResource(
                            view as Activity,
                            R.string.invalid_credentials
                        )
                    )
                }.await()
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

