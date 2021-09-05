package com.istea.nutritechmobile.ui.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.istea.nutritechmobile.*


class Pagina_Principal : AppCompatActivity() {
    private lateinit var nombrePaciente: String
    private lateinit var apellidoPaciente: String
    private lateinit var txtUsuarioBienvenida: TextView
    private lateinit var txtFraseDelDia: TextView
    private lateinit var txtAutorDelDia: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina_principal)
        setupUI()
    }

    override fun onResume() {
        showUserData()
        generateQuoteOfTheDay()
        super.onResume()
    }

    private fun setupUI() {
        txtUsuarioBienvenida = findViewById(R.id.txtMensajeBienvenida)
        txtFraseDelDia = findViewById(R.id.txtFraseDelDia)
        txtAutorDelDia = findViewById(R.id.txtAutorDelDia)
    }

    private fun showUserData() {
        nombrePaciente = intent.extras?.getString("Nombre") ?: "Not found"
        apellidoPaciente = intent.extras?.getString("Apellido") ?: "Not found"
        txtUsuarioBienvenida.text = "Bienvenido $nombrePaciente $apellidoPaciente".uppercase()
    }

    private fun generateQuoteOfTheDay() {
        val frase = getString(R.string.daily_phrase_test)
        val autor = getString(R.string.daily_author_test)
        txtFraseDelDia.text = "\"${frase}\""
        txtAutorDelDia.text = autor
    }

}