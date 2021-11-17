package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.Plan
import com.istea.nutritechmobile.firebase.FirebaseAuthManager
import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.model.PlanDisplayRepositoryImp
import com.istea.nutritechmobile.presenter.PlanDisplayPresenterImp
import com.istea.nutritechmobile.presenter.interfaces.IPlanDisplayPresenter
import com.istea.nutritechmobile.ui.interfaces.IPlanDisplayView
import kotlinx.coroutines.Dispatchers
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
    private lateinit var contenedorSinPlan: LinearLayout
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var scrollView: ScrollView
    private val planDisplayPresenter: IPlanDisplayPresenter by lazy {
        PlanDisplayPresenterImp(this, PlanDisplayRepositoryImp(FirebaseFirestoreManager(this)))
    }

    private val fireAuthManager: FirebaseAuthManager by lazy {
        FirebaseAuthManager()
    }

    private val noData = "-- No hay información para este campo --"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_display)
        setupUI()

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

    override fun setupUI() {
        Nombretv = findViewById(R.id.Nombretv)
        Tipotv = findViewById(R.id.Tipotv)
        CantAguaDiariaTv = findViewById(R.id.CantAguaDiariaTv)
        CantColacionesDiariasTv = findViewById(R.id.CantColacionesDiariasTv)
        Desayunotv = findViewById(R.id.Desayunotv)
        Almuerzotv = findViewById(R.id.Almuerzotv)
        Meriendatv = findViewById(R.id.Meriendatv)
        Cenatv = findViewById(R.id.Cenatv)
        Colaciontv = findViewById(R.id.Colaciontv)
        scrollView = findViewById(R.id.scrollView)
        contenedorSinPlan = findViewById(R.id.noPlanContainer)
        bottomNavBar = findViewById(R.id.bottomNavigationView)
        bottomNavBar.selectedItemId = R.id.home
        setupBottomNavigationBar(bottomNavBar)
        setupToolbar()

        lifecycleScope.launch(Dispatchers.Main) {
            planDisplayPresenter.fillPlanInfo(fireAuthManager.getAuthEmail())
        }
    }

    override suspend fun fillDataView(dataRepo: Plan?) {
        if (dataRepo != null) {
            showPlanAlimentacion()
            Nombretv.text = dataRepo.Nombre.uppercase()
            Tipotv.text = dataRepo.Tipo.uppercase()

            CantAguaDiariaTv.text = dataRepo.CantAguaDiaria.toString()
            CantColacionesDiariasTv.text = dataRepo.CantColacionesDiarias.toString()
            if (dataRepo.Desayuno.isNotEmpty()) {
                Desayunotv.text = dataRepo.Desayuno.joinToString("\n")
            } else {
                Desayunotv.text = this.noData
            }
            if (dataRepo.Almuerzo.isNotEmpty()) {
                Almuerzotv.text = dataRepo.Almuerzo.joinToString("\n")
            } else {
                Almuerzotv.text = this.noData
            }
            if (dataRepo.Merienda.isNotEmpty()) {
                Meriendatv.text = dataRepo.Merienda.joinToString("\n")
            } else {
                Meriendatv.text = this.noData
            }
            if (dataRepo.Cena.isNotEmpty()) {
                Cenatv.text = dataRepo.Cena.joinToString("\n")
            } else {
                Cenatv.text = this.noData
            }
            if (dataRepo.Colacion.isNotEmpty()) {
                Colaciontv.text = dataRepo.Colacion.joinToString("\n")
            } else {
                Colaciontv.text = this.noData
            }
        } else {
            hidePlanAlimentacion()
        }
    }

    private fun hidePlanAlimentacion() {
        scrollView.isVisible = false
        contenedorSinPlan.isVisible = true
    }

    private fun showPlanAlimentacion() {
        contenedorSinPlan.isVisible = false
        scrollView.isVisible = true
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
        //Nothing here for the moment
    }

    override fun goToHomeView() {
        finish()
    }

    override fun goToDailyRegistryView() {
        Intent(this@PlanDisplayActivity, DailyRegistryActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun goToProgressView() {
        Intent(this@PlanDisplayActivity, RegistroCorporalActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun goToProfileView() {
        Intent(this@PlanDisplayActivity, PerfilPacienteActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

}