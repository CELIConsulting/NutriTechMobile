package com.istea.nutritechmobile.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import com.istea.nutritechmobile.R

class LoginActivity : AppCompatActivity() {
    private lateinit var etLoginMail: EditText
    private lateinit var etLoginPassword: EditText
    private lateinit var btnLogin: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NutriTechMobile)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupUI()
    }

    //TODO Inicializar componentes de la pantalla de login
    private fun setupUI() {
        etLoginMail = findViewById(R.id.etLoginMail)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            //TODO login()
        }

    }

}
