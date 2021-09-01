package com.istea.nutritechmobile.ui.interfaces

import com.istea.nutritechmobile.data.User
import com.istea.nutritechmobile.data.UserResponse

interface ILoginView {
    fun showMessage(message: String)
    fun goToNextScreen(user: UserResponse)
}
