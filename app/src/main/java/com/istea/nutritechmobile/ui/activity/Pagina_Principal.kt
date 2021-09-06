package com.istea.nutritechmobile.ui.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.istea.nutritechmobile.*
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.presenter.PrincipalPresenterImp
import com.istea.nutritechmobile.presenter.interfaces.IPrincipalPresenter
import com.istea.nutritechmobile.ui.interfaces.IPrincipalView


class Pagina_Principal : AppCompatActivity(), IPrincipalView {
    private lateinit var txtUsuarioBienvenida: TextView
    private lateinit var txtFraseDelDia: TextView
    private lateinit var txtAutorDelDia: TextView
    private lateinit var btnVerPlan: MaterialButton
    private lateinit var btnModifPlan: MaterialButton
    private val principalPresenter: IPrincipalPresenter by lazy {
        PrincipalPresenterImp(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina_principal)
        setupUI()
    }

    override fun onResume() {
        principalPresenter.loggedUserData()
        principalPresenter.getQuoteOfTheDay()
        super.onResume()
    }

    private fun setupUI() {
        txtUsuarioBienvenida = findViewById(R.id.txtMensajeBienvenida)
        txtFraseDelDia = findViewById(R.id.txtFraseDelDia)
        txtAutorDelDia = findViewById(R.id.txtAutorDelDia)
        btnVerPlan = findViewById(R.id.btnVerPlan)
        btnModifPlan = findViewById(R.id.btnModifPlan)

        btnVerPlan.setOnClickListener {
            showInProgressMessage()
        }

        btnModifPlan.setOnClickListener {
            showInProgressMessage()
        }

    }

    override fun welcomeUser(name: String, lastName: String) {
        txtUsuarioBienvenida.text = "Bienvenido $name $lastName".uppercase()
    }

    override fun generateQuoteOfTheDay(phrase: String, author: String) {
        txtFraseDelDia.text = "\"${phrase}\""
        txtAutorDelDia.text = author
    }

    override fun showInProgressMessage() {
        UIManager.showMessageShort(this, "Función en desarrollo")
    }

    override fun goBackToLogin() {
        Intent(this@Pagina_Principal, LoginActivity::class.java).apply {
            startActivity(this)
        }
    }

}