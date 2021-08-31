package com.istea.nutritechmobile.model.interfaces

import com.istea.nutritechmobile.presenter.interfaces.ILoginPresenter

class LoginRepositoryImp(
    private val presenter: ILoginPresenter
) : ILoginRepository {
}