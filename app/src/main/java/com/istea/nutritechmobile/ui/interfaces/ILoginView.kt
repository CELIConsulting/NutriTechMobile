package com.istea.nutritechmobile.ui.interfaces

import com.istea.nutritechmobile.data.User

interface ILoginView {
    fun showMessage(message: String)
    fun goToNextScreen(user: User)
}
