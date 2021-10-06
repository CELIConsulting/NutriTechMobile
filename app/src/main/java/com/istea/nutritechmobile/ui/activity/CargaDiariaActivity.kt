package com.istea.nutritechmobile.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.io.FireStoreHelper
import com.istea.nutritechmobile.model.CargaDiariaRepositoryImp
import com.istea.nutritechmobile.presenter.CargaDiariaPresenterImp
import com.istea.nutritechmobile.presenter.interfaces.ICargaDiariaPresenter
import com.istea.nutritechmobile.ui.interfaces.ICargaDiariaView
import com.istea.nutritechmobile.utils.Camera

class CargaDiariaActivity : AppCompatActivity(), ICargaDiariaView {

    private lateinit var camera: Camera
    private lateinit var toolbar: Toolbar

    private val cargaDiariaPresenter: ICargaDiariaPresenter by lazy {
        CargaDiariaPresenterImp(this, CargaDiariaRepositoryImp(FireStoreHelper(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carga_diaria)
        setupUi()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupUi(){

    }
}