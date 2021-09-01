package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.ui.interfaces.ILoginView
import com.istea.nutritechmobile.helpers.getTextFrom
import com.istea.nutritechmobile.io.FireStoreHelper
import com.istea.nutritechmobile.model.LoginRepositoryImp
import com.istea.nutritechmobile.presenter.LoginPresenterImp
import com.istea.nutritechmobile.presenter.interfaces.ILoginPresenter
import kotlinx.coroutines.launch

private const val TAG_ACTIVITY = "LoginActivity"
private const val LOGGED_USER = "LOGGED_USER"

class LoginActivity : AppCompatActivity(), ILoginView {
    private lateinit var etLoginMail: EditText
    private lateinit var etLoginPassword: EditText
    private lateinit var btnLogin: MaterialButton
    private val loginPresenter: ILoginPresenter by lazy {
        LoginPresenterImp(this, LoginRepositoryImp(FireStoreHelper(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NutriTechMobile)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupUI()
    }

    private fun setupUI() {
        etLoginMail = findViewById(R.id.etLoginMail)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            lifecycleScope.launch {
                Log.d(TAG_ACTIVITY, "Coroutine: begin")
                login()
                Log.d(TAG_ACTIVITY, "Coroutine: End")
            }

        }
    }

    private suspend fun login() {
        val mail: String = getTextFrom(etLoginMail)
        val password: String = getTextFrom(etLoginPassword)
        Log.d(TAG_ACTIVITY, "Mail: $mail | Password: $password")

        loginPresenter.doLogin(mail, password)
    }

    override fun showMessage(message: String) {
        UIManager.showMessageShort(this, message)
    }

    override fun goToNextScreen(user: UserResponse) {
        Log.d(TAG_ACTIVITY, "User: ${user.Nombre} Rol: ${user.Apellido}")

        //TODO: OBAMA FIX THIS
        Intent(this@LoginActivity, Pagina_Principal::class.java).apply {
            putExtra("Nombre", user.Nombre)
            putExtra("Apellido", user.Apellido)
            startActivity(this)
        }
    }


}
