package com.istea.nutritechmobile.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView
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
        supportActionBar?.setDisplayShowTitleEnabled(false);
    }

    private fun setupBottomNavigationBar(){
        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.registro_diario -> {
                    goToDailyRegistry()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.recetas -> {
                    Log.e("Pagina Principal", " recetas")
                    return@OnNavigationItemSelectedListener true

                }
                R.id.progreso -> {
                    Log.e("Pagina Principal", " progreso")
                    return@OnNavigationItemSelectedListener true

                }
                R.id.info_personal -> {
                    Log.e("Pagina Principal", " info")
                    return@OnNavigationItemSelectedListener true

                }
            }
            false
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        principalPresenter.loggedUserData()
        principalPresenter.getQuoteOfTheDay()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_superior, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupUI() {
        txtUsuarioBienvenida = findViewById(R.id.txtMensajeBienvenida)
        txtFraseDelDia = findViewById(R.id.txtFraseDelDia)
        txtAutorDelDia = findViewById(R.id.txtAutorDelDia)
        btnVerPlan = findViewById(R.id.btnVerPlan)
        btnModifPlan = findViewById(R.id.btnModifPlan)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        btnVerPlan.setOnClickListener {
            goToPlanView()
        }

        btnModifPlan.setOnClickListener {
            showInProgressMessage()
        }

        setupToolbar()
        setupBottomNavigationBar()
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

    override fun goToPlanView() {
        val intent = intent

        Intent(this@Pagina_Principal, PlanDisplayActivity::class.java).apply {
            putExtra("Email", intent.getStringExtra("Email"))
            startActivity(this)
        }
    }
    override fun goToDailyRegistry(){
        Intent(this@Pagina_Principal, CargaDiariaActivity::class.java).apply {
            startActivity(this)
        }
    }
}