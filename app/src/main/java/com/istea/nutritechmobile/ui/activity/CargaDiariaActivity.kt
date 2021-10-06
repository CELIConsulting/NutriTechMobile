package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
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


    private lateinit var imgFoodUpload: ImageView
    private lateinit var btnTakeCapture: ImageButton
    private lateinit var btnDeleteCapture: ImageButton
    private lateinit var chkDoExcersice: CheckBox
    private lateinit var etObservacions: EditText
    private lateinit var btnSubmit: Button

    private lateinit var camera: Camera
    private lateinit var toolbar: Toolbar

    private val cargaDiariaPresenter: ICargaDiariaPresenter by lazy {
        CargaDiariaPresenterImp(this, CargaDiariaRepositoryImp(FireStoreHelper(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carga_diaria)
        setupUi()
        bindEvents()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupUi() {
        imgFoodUpload = findViewById(R.id.imgFoodUpload)
        btnTakeCapture = findViewById(R.id.btnTakeCapture)
        btnDeleteCapture = findViewById(R.id.btnDeleteCapture)
        chkDoExcersice = findViewById(R.id.chkDoExcersice)
        etObservacions = findViewById(R.id.etObservacions)
        btnSubmit = findViewById(R.id.btnSubmit)
        camera = Camera(this, imgFoodUpload)
        setupToolbar()
    }

    private fun bindEvents() {
        btnTakeCapture.setOnClickListener {
            camera.takePhoto()
        }
        btnDeleteCapture.setOnClickListener {
            camera.cleanPhoto()
        }
        btnSubmit.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        camera.activityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camera.requestPermissionsResult(requestCode, permissions, grantResults)
    }
}