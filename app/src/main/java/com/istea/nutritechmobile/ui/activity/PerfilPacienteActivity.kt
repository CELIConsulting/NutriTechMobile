package com.istea.nutritechmobile.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.istea.nutritechmobile.R

class PerfilPacienteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_scroll)
        setupUI()
    }

    private fun setupUI() {
        println("Setting up UI...")
    }
}