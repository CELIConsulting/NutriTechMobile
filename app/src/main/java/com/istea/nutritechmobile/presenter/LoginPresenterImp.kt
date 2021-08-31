package com.istea.nutritechmobile.presenter

import com.istea.nutritechmobile.model.interfaces.ILoginRepository
import com.istea.nutritechmobile.presenter.interfaces.ILoginPresenter
import com.istea.nutritechmobile.ui.interfaces.ILoginView

class LoginPresenterImp(
    private val view: ILoginView,
    private val repo: ILoginRepository
) :
    ILoginPresenter {
    override fun doLogin(mail: String, password: String) {
        TODO("Not yet implemented")
    }
}