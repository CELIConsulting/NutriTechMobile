package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.helpers.CameraManager
import com.istea.nutritechmobile.model.CargaDiariaRepositoryImp
import com.istea.nutritechmobile.presenter.CargaDiariaPresenterImp
import com.istea.nutritechmobile.presenter.interfaces.ICargaDiariaPresenter
import com.istea.nutritechmobile.ui.interfaces.ICargaDiariaView

class CargaDiariaActivity : AppCompatActivity(), ICargaDiariaView {


    private lateinit var imgFoodUpload: ImageView
    private lateinit var btnTakeCapture: ImageButton
    private lateinit var btnDeleteCapture: ImageButton
    private lateinit var chkDoExcersice: CheckBox
    private lateinit var etObservacions: EditText
    private lateinit var btnSubmit: Button

    private lateinit var camera: CameraManager
    private lateinit var hiddenFileUpload: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView

    private val cargaDiariaPresenter: ICargaDiariaPresenter by lazy {
        CargaDiariaPresenterImp(this, CargaDiariaRepositoryImp(FirebaseFirestoreManager(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carga_diaria)
        setupUi()
        setupBottomNavigationBar()
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
        camera = CameraManager(this, imgFoodUpload, hiddenFileUpload)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
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

    private fun goToDailyRegistry() {
        Intent(this@CargaDiariaActivity, CargaDiariaActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun setupBottomNavigationBar() {
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
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
}