package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.helpers.getTextFrom
import com.istea.nutritechmobile.io.FireStoreHelper
import com.istea.nutritechmobile.model.LoginRepositoryImp
import com.istea.nutritechmobile.presenter.LoginPresenterImp
import com.istea.nutritechmobile.presenter.interfaces.ILoginPresenter
import com.istea.nutritechmobile.ui.interfaces.ILoginView
import kotlinx.coroutines.launch

const val LOGGED_USER = "LOGGED_USER"
private const val TAG_ACTIVITY = "LoginActivity"

class LoginActivity : AppCompatActivity(), ILoginView {
    private lateinit var etLoginMail: EditText
    private lateinit var etLoginPassword: EditText
    private lateinit var btnLogin: MaterialButton
    private val loginPresenter: ILoginPresenter by lazy {
        LoginPresenterImp(this, LoginRepositoryImp(FireStoreHelper(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun goToNextScreen(currentUser: UserResponse) {
        try {
            Log.d(TAG_ACTIVITY, "SENDING OBJECT TO MAIN ACTIVITY")

            Intent(this@LoginActivity, PaginaPrincipalActivity::class.java).apply {
                putExtra(LOGGED_USER, currentUser)
                startActivity(this)
            }

            Log.d(TAG_ACTIVITY, "SENDING OBJECT OK!")
        } catch (e: Exception) {
            Log.d(TAG_ACTIVITY, "SENDING OBJECT ERROR! BECAUSE ${e.message?.uppercase()}")
        }


    }


}
