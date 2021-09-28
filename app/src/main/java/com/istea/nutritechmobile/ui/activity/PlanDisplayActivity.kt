package com.istea.nutritechmobile.ui.activity

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.Plan
import com.istea.nutritechmobile.io.FireStoreHelper
import com.istea.nutritechmobile.model.PlanDisplayRepositoryImp
import com.istea.nutritechmobile.presenter.PlanDisplayPresenterImp
import com.istea.nutritechmobile.presenter.interfaces.IPlanDisplayPresenter
import com.istea.nutritechmobile.ui.interfaces.IPlanDisplayView
import kotlinx.coroutines.launch

class PlanDisplayActivity : AppCompatActivity(), IPlanDisplayView {

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
    private val planDisplayPresenter: IPlanDisplayPresenter by lazy {
        PlanDisplayPresenterImp(this, PlanDisplayRepositoryImp(FireStoreHelper(this)))
    }
    private val noData = "-- No data --"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_display)
        setupUI()
    }
    override fun onResume() {

        super.onResume()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Hiding default app icon
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_superior, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupUI() {
        Nombretv = findViewById(R.id.Nombretv)
        Tipotv = findViewById(R.id.Tipotv)
        CantAguaDiariaTv = findViewById(R.id.CantAguaDiariaTv)
        CantColacionesDiariasTv = findViewById(R.id.CantColacionesDiariasTv)
        Desayunotv = findViewById(R.id.Desayunotv)
        Almuerzotv = findViewById(R.id.Almuerzotv)
        Meriendatv = findViewById(R.id.Meriendatv)
        Cenatv = findViewById(R.id.Cenatv)
        Colaciontv = findViewById(R.id.Colaciontv)
        setupToolbar()

        lifecycleScope.launch{
            planDisplayPresenter.fillPlanInfo(intent.getStringExtra("Email"))
        }
    }

    override suspend fun fillDataView(dataRepo: Plan?) {
        if (dataRepo != null) {
            Nombretv.text = dataRepo.Nombre
            Tipotv.text = dataRepo.Tipo

            CantAguaDiariaTv.text = dataRepo.CantAguaDiaria.toString()
            CantColacionesDiariasTv.text = dataRepo.CantColacionesDiarias.toString()
            if (dataRepo.Desayuno.isNotEmpty()){
                Desayunotv.text = dataRepo.Desayuno.joinToString("\n")
            }else {
                Desayunotv.text = this.noData
            }
            if (dataRepo.Almuerzo.isNotEmpty()){
                Almuerzotv.text = dataRepo.Almuerzo.joinToString("\n")
            }else {
                Almuerzotv.text = this.noData
            }
            if (dataRepo.Merienda.isNotEmpty()){
                Meriendatv.text = dataRepo.Merienda.joinToString("\n")
            }else {
                Meriendatv.text =  this.noData
            }
            if (dataRepo.Cena.isNotEmpty()){
                Cenatv.text = dataRepo.Cena.joinToString("\n")
            }else {
                Cenatv.text =  this.noData
            }
            if (dataRepo.Colacion.isNotEmpty()){
                Colaciontv.text = dataRepo.Colacion.joinToString("\n")
            }else {
                Colaciontv.text =  this.noData
            }
        }
    }
}