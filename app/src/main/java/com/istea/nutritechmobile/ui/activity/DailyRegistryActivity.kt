package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.helpers.CameraManager
import com.istea.nutritechmobile.helpers.NOTIMPLEMENTEDYET
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.model.DailyRegistryRepositoryImp
import com.istea.nutritechmobile.presenter.DailyRegistryPresenterImp
import com.istea.nutritechmobile.presenter.interfaces.IDailyRegistryPresenter
import com.istea.nutritechmobile.ui.interfaces.ICargaDiariaView
import com.istea.nutritechmobile.ui.interfaces.IToolbar

private const val TAG = "DailyRegistryActivity"

class DailyRegistryActivity : AppCompatActivity(), ICargaDiariaView, IToolbar {

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

    private val DailyRegistryPresenter: IDailyRegistryPresenter by lazy {
        DailyRegistryPresenterImp(this, DailyRegistryRepositoryImp(FirebaseFirestoreManager(this)))
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

    private fun setupUi() {
        imgFoodUpload = findViewById(R.id.imgFoodUpload)
        btnTakeCapture = findViewById(R.id.btnTakeCapture)
        btnDeleteCapture = findViewById(R.id.btnDeleteCapture)
        chkDoExcersice = findViewById(R.id.chkDoExcersice)
        etObservacions = findViewById(R.id.etObservacions)
        btnSubmit = findViewById(R.id.btnSubmit)
        hiddenFileUpload = findViewById(R.id.hiddenFileUpload)
        camera = CameraManager(this, imgFoodUpload, hiddenFileUpload)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        setupToolbar()
        setupBottomNavigationBar(bottomNavigationView)
        bindEvents()
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

    override fun goToDailyRegistryView() {
        Intent(this@DailyRegistryActivity, DailyRegistryActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun goToProfileView() {
        Intent(this@DailyRegistryActivity, PerfilPacienteActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun goToRecipesView() {
        UIManager.showMessageShort(this, NOTIMPLEMENTEDYET)
    }

    override fun goToProgressView() {
        UIManager.showMessageShort(this, NOTIMPLEMENTEDYET)
    }

    override fun setupBottomNavigationBar(bottomNavigationView: BottomNavigationView) {
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.registro_diario -> {
                        goToDailyRegistryView()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.recetas -> {
                        goToRecipesView()
                        return@OnNavigationItemSelectedListener true

                    }
                    R.id.progreso -> {
                        goToProgressView()
                        return@OnNavigationItemSelectedListener true

                    }
                    R.id.info_personal -> {
                        goToProfileView()
                        return@OnNavigationItemSelectedListener true

                    }
                }
                false
            }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}