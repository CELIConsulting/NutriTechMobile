package com.istea.nutritechmobile.ui.interfaces

import com.istea.nutritechmobile.data.UserResponse

interface ILoginView {
    fun showMessage(message: String)
    fun goToTyCScreen()
    fun goToMainView()
}
