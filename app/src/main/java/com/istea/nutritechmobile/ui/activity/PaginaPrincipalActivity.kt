package com.istea.nutritechmobile.ui.activity


import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.istea.nutritechmobile.*
import com.istea.nutritechmobile.helpers.NOTIMPLEMENTEDYET
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.helpers.preferences.SessionManager
import com.istea.nutritechmobile.presenter.PrincipalPresenterImp
import com.istea.nutritechmobile.presenter.interfaces.IPrincipalPresenter
import com.istea.nutritechmobile.ui.interfaces.IPrincipalView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG_ACTIVITY = "PrincipalActivity"

class PaginaPrincipalActivity : AppCompatActivity(), IPrincipalView {
    private lateinit var txtUsuarioBienvenida: TextView
    private lateinit var txtFraseDelDia: TextView
    private lateinit var txtAutorDelDia: TextView
    private lateinit var btnVerPlan: MaterialButton
    private lateinit var btnModifPlan: MaterialButton
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavBar: BottomNavigationView

    private val principalPresenter: IPrincipalPresenter by lazy {
        PrincipalPresenterImp(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina_principal)
        setupUI()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Hiding default app icon
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupUI() {
        txtUsuarioBienvenida = findViewById(R.id.txtMensajeBienvenida)
        txtFraseDelDia = findViewById(R.id.txtFraseDelDia)
        txtAutorDelDia = findViewById(R.id.txtAutorDelDia)
        btnVerPlan = findViewById(R.id.btnVerPlan)
        btnModifPlan = findViewById(R.id.btnModifPlan)
        bottomNavBar = findViewById(R.id.bottomNavigationView)
        bottomNavBar.selectedItemId = R.id.home
        btnVerPlan.setOnClickListener {
            goToPlanView()
        }

        btnModifPlan.setOnClickListener {
            showInProgressMessage()
        }
        setupToolbar()
        setupBottomNavigationBar(bottomNavBar)
    }

    override fun onResume() {
        lifecycleScope.launch(Dispatchers.Main) {
            principalPresenter.loggedUserData()
        }

        principalPresenter.getQuoteOfTheDay()
        super.onResume()
    }


    override fun welcomeUser(name: String, lastName: String) {
        txtUsuarioBienvenida.text = "Bienvenido $name $lastName".uppercase()
    }

    override fun generateQuoteOfTheDay(phrase: String, author: String) {
        txtFraseDelDia.text = "\"${phrase}\""
        txtAutorDelDia.text = author
    }

    override fun showInProgressMessage() {
        UIManager.showMessageShort(this, NOTIMPLEMENTEDYET)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_superior, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun setupBottomNavigationBar(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    goToHomeView()
                    return@setOnItemSelectedListener true
                }
                R.id.registro_diario -> {
                    goToDailyRegistryView()
                    return@setOnItemSelectedListener true
                }
                R.id.progreso -> {
                    goToProgressView()
                    return@setOnItemSelectedListener true
                }
                R.id.info_personal -> {
                    goToProfileView()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun goToLoginView() {
        GlobalScope.launch(Dispatchers.IO) {
            userLogout()
        }

        Intent(this@PaginaPrincipalActivity, LoginActivity::class.java).apply {
            flags = FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

    override fun refreshActivity() {
        finish()
        overridePendingTransition(0, 0)
        Intent(this@PaginaPrincipalActivity, this::class.java).apply {
            startActivity(this)
        }
        overridePendingTransition(0, 0)
    }

    override fun goToHomeView() {
        refreshActivity()
    }

    override fun goToPlanView() {

        Intent(this@PaginaPrincipalActivity, PlanDisplayActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun goToDailyRegistryView() {
        Intent(this@PaginaPrincipalActivity, DailyRegistryActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun goToProgressView() {
        Intent(this@PaginaPrincipalActivity, RegistroCorporalActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun goToProfileView() {
        Intent(this@PaginaPrincipalActivity, PerfilPacienteActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun onBackPressed() {
        goToLoginView()
        super.onBackPressed()
    }

//    private fun resetBottomNavBar(){
//        bottomNavBar.selectedItemId = R.id.home
//    }

    private suspend fun userLogout() {
        SessionManager.saveLoggedUser(null)
    }
}