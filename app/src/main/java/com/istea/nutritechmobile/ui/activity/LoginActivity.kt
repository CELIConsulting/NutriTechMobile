package com.istea.nutritechmobile.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.ui.interfaces.ILoginView
import com.istea.nutritechmobile.helpers.getTextFrom
import com.istea.nutritechmobile.helpers.getTextFromResource
import com.istea.nutritechmobile.helpers.mailFormatIsValid

//private const val TAG_ACTIVITY = "LoginActivity"

class LoginActivity : AppCompatActivity(), ILoginView {
    private lateinit var etLoginMail: EditText
    private lateinit var etLoginPassword: EditText
    private lateinit var btnLogin: MaterialButton
//    private val loginPresenter: ILoginPresenter by lazy {
//        //TODO: Inicializar clase de loginPresenter
//        //TODO: Requiere una vista y un repositorio
//        LoginPresenterImp(this, ....)
//    }

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
            //TODO login()
            validateLogin()
        }

    }

    private fun validateLogin() {
        val mail: String = getTextFrom(etLoginMail)
        val password: String = getTextFrom(etLoginPassword)

        if (checkEmptyInputs(mail, password)) {
            if (mailFormatIsValid(mail)) {
                showMessage("DATOS VALIDOS PARA SER PROCESADOS")
                //loginPresenter.doLogin(mail, password)
            } else
                showMessage(getTextFromResource(this, R.string.mail_format_invalid))
        }

    }

    private fun checkEmptyInputs(mail: String, pass: String): Boolean {
        val mailEmpty = mail.isEmpty()
        val passEmpty = pass.isEmpty()

        if (mailEmpty && passEmpty)
            showMessage(getTextFromResource(this, R.string.both_empty))
        else {
            when {
                passEmpty -> showMessage(getTextFromResource(this, R.string.pass_empty))
                mailEmpty -> showMessage(getTextFromResource(this, R.string.mail_empty))
                else -> return true
            }
        }
        return false
    }


    override fun showMessage(message: String) {
        UIManager.showMessageShort(this, message)
    }


}
