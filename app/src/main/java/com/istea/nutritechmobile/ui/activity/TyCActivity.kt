package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.istea.nutritechmobile.R


class TyCActivity : AppCompatActivity(), ICompletarPerfilListener {
    private lateinit var chkAceptacionTyc: MaterialCheckBox
    private lateinit var btnContinuar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tyc)
        setupUI()
    }

    private fun setupUI() {
        chkAceptacionTyc = findViewById(R.id.chk_aceptacion_TyC)
        btnContinuar = findViewById(R.id.btn_continuar)
        btnContinuar.isEnabled = false

        chkAceptacionTyc.setOnCheckedChangeListener { _, aceptoTyC ->
            validarAceptacionTyc(aceptoTyC)
        }

        btnContinuar.setOnClickListener {
            launchAlertDialog()
        }
    }

    private fun launchAlertDialog() {
        CompletarPerfilDialogFragment().show(
            supportFragmentManager,
            CompletarPerfilDialogFragment.FRAGMENT_ID
        )
    }

    private fun validarAceptacionTyc(aceptoFlag: Boolean) {
        btnContinuar.isEnabled = aceptoFlag
    }

    override fun onPositiveClick() {
        goToProfileScreen()
    }

    override fun onNegativeClick() {
        goToLoginScreen()
    }

    private fun goToProfileScreen() {
        Intent(this, PerfilPacienteActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    private fun goToLoginScreen() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

}