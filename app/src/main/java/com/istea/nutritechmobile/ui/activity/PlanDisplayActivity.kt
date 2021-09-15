package com.istea.nutritechmobile.ui.activity

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.istea.nutritechmobile.R

class PlanDisplayActivity : AppCompatActivity() {

    private lateinit var Nombretv: TextView
    private lateinit var Tipotv: TextView
    private lateinit var CantAguaDiariaTv: TextView
    private lateinit var CantColacionesDiariasTv: TextView
    private lateinit var Desayunotv: TextView
    private lateinit var Almuerzotv: TextView
    private lateinit var Meriendatv: TextView
    private lateinit var Cenatv: TextView
    private lateinit var Colaciontv: TextView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_display)
        setupUi()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Hiding default app icon
        supportActionBar?.setDisplayShowTitleEnabled(false);

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_superior, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupUi(){
        Nombretv= findViewById(R.id.Nombretv)
        Tipotv= findViewById(R.id.Tipotv)
        CantAguaDiariaTv= findViewById(R.id.CantAguaDiariaTv)
        CantColacionesDiariasTv= findViewById(R.id.CantColacionesDiariasTv)
        Desayunotv= findViewById(R.id.Desayunotv)
        Almuerzotv= findViewById(R.id.Almuerzotv)
        Meriendatv= findViewById(R.id.Meriendatv)
        Cenatv= findViewById(R.id.Cenatv)
        Colaciontv= findViewById(R.id.Colaciontv)
        setupToolbar()
    }
}