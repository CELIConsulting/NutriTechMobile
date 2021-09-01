package com.istea.nutritechmobile.ui.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.istea.nutritechmobile.*


class Pagina_Principal : AppCompatActivity() {
    private lateinit var nombrePaciente: String
    private lateinit var apellidoPaciente: String
    private lateinit var txtUsuarioBienvenida: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina_principal)
        setupUI()
    }

    override fun onResume() {
        showUserData()
        super.onResume()
    }

    private fun setupUI() {
        txtUsuarioBienvenida = findViewById(R.id.tvLeyenda2)
    }

    private fun showUserData() {
        nombrePaciente = intent.extras?.getString("Nombre").toString()
        apellidoPaciente = intent.extras?.getString("Apellido").toString()

        txtUsuarioBienvenida.text = "$nombrePaciente $apellidoPaciente"
    }


}