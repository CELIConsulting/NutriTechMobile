package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import android.content.Intent.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.firebase.Timestamp
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.extensions.dateFromString
import com.istea.nutritechmobile.helpers.extensions.stringFromDate
import com.istea.nutritechmobile.helpers.preferences.SessionManager
import com.istea.nutritechmobile.firebase.FirebaseFirestoreManager
import com.istea.nutritechmobile.helpers.UIManager
import com.istea.nutritechmobile.model.PerfilPacienteRepositoryImp
import com.istea.nutritechmobile.presenter.PerfilPacientePresenterImp
import com.istea.nutritechmobile.presenter.interfaces.IPerfilPacientePresenter
import com.istea.nutritechmobile.ui.interfaces.IPerfilPacienteView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

private const val CAMPO_NO_ASIGNADO = "No asignado"
private const val TAG_ACTIVITY = "PerfilPacienteActivity"
const val FIRST_LOGIN = "FirstLogin"
const val USER_TO_UPDATE = "UserToUpdate"

class PerfilPacienteActivity : AppCompatActivity(), IPerfilPacienteView {
    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etMail: EditText
    private lateinit var etFechaNacimiento: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etAltura: EditText
    private lateinit var etPeso: EditText
    private lateinit var etMedidaCintura: EditText
    private lateinit var etTipoAlimentacion: EditText
    private lateinit var btnUpdate: MaterialButton
    private lateinit var bottomNavBar: BottomNavigationView
    private var calendar: GregorianCalendar = GregorianCalendar()
    private var pacienteLogueado: UserResponse? = null
    private var firstLoginFlag: Boolean = false

    private val perfilPresenter: IPerfilPacientePresenter by lazy {
        PerfilPacientePresenterImp(
            this,
            PerfilPacienteRepositoryImp(FirebaseFirestoreManager(this))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_scroll)
        setupUI()
    }

    override fun onResume() {
        getPaciente()
        super.onResume()
    }

    private fun getPaciente() {
        lifecycleScope.launch(Dispatchers.Main) {
            perfilPresenter.getPaciente()
        }
    }

    private fun setupUI() {
        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        etMail = findViewById(R.id.etMail)
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento)
        etTelefono = findViewById(R.id.etTelefono)
        etPeso = findViewById(R.id.etPeso)
        etAltura = findViewById(R.id.etAltura)
        etMedidaCintura = findViewById(R.id.etMedidaCintura)
        etTipoAlimentacion = findViewById(R.id.etTipoAlimentacion)
        btnUpdate = findViewById(R.id.btnUpdate)
        bottomNavBar = findViewById(R.id.bottomNavigationView)
        bottomNavBar.selectedItemId = R.id.info_personal
        setupBottomNavigationBar(bottomNavBar)
        disableRegistroCorporalFields()

    }

    override fun showPacienteInfo(paciente: UserResponse) {
        pacienteLogueado = paciente

        //Completar los campos
        etNombre.setText(if (paciente.Nombre.isNotEmpty()) paciente.Nombre else CAMPO_NO_ASIGNADO)
        etApellido.setText(if (paciente.Apellido.isNotEmpty()) paciente.Apellido else CAMPO_NO_ASIGNADO)
        etMail.setText(if (paciente.Email.isNotEmpty()) paciente.Email else CAMPO_NO_ASIGNADO)
        etFechaNacimiento.setText(getFechaNacimiento(paciente.FechaNacimiento))
        etTelefono.setText(if (paciente.Telefono.isNotEmpty()) paciente.Telefono else CAMPO_NO_ASIGNADO)
        etAltura.setText(if (paciente.Altura != null && paciente.Altura!! > 0) paciente.Altura.toString() else CAMPO_NO_ASIGNADO)
        etPeso.setText(if (paciente.Peso != null && paciente.Peso!! > 0) paciente.Peso.toString() else CAMPO_NO_ASIGNADO)
        etMedidaCintura.setText(if (paciente.MedidaCintura != null && paciente.MedidaCintura!! > 0) paciente.MedidaCintura.toString() else CAMPO_NO_ASIGNADO)
        etTipoAlimentacion.setText(if (paciente.TipoAlimentacion.isNotEmpty()) paciente.TipoAlimentacion else CAMPO_NO_ASIGNADO)
    }

    private fun buildPacienteFromForm(): UserResponse {
        val updatedUser = pacienteLogueado ?: UserResponse()

        updatedUser.Apellido = etApellido.text.toString()
        updatedUser.Nombre = etNombre.text.toString()
        updatedUser.Email = etMail.text.toString()
        updatedUser.FechaNacimiento = setFechaNacimiento()
        updatedUser.TyC = true

        updatedUser.Telefono = if (etTelefono.text.toString()
                .isNotEmpty() && etTelefono.text.toString() != CAMPO_NO_ASIGNADO
        ) etTelefono.text.toString() else ""
        updatedUser.TipoAlimentacion = if (etTipoAlimentacion.text.toString()
                .isNotEmpty() && etTipoAlimentacion.text.toString() != CAMPO_NO_ASIGNADO
        ) etTipoAlimentacion.text.toString() else ""
        updatedUser.Altura =
            if (etAltura.text.toString()
                    .isNotEmpty() && etAltura.text.toString() != CAMPO_NO_ASIGNADO
            ) etAltura.text.toString()
                .toFloat() else null
        updatedUser.Peso =
            if (etPeso.text.toString()
                    .isNotEmpty() && etPeso.text.toString() != CAMPO_NO_ASIGNADO
            ) etPeso.text.toString().toFloat() else 0f
        updatedUser.MedidaCintura =
            if (etMedidaCintura.text.toString()
                    .isNotEmpty() && etMedidaCintura.text.toString() != CAMPO_NO_ASIGNADO
            ) etMedidaCintura.text.toString()
                .toFloat() else 0f

        return updatedUser;
    }

    override fun updatePacienteInfo() {
        val paciente = buildPacienteFromForm()

        lifecycleScope.launch(Dispatchers.Main) {
            perfilPresenter.updatePaciente(paciente)
        }

    }

    override fun isUserFirstLogin(firstTime: Boolean) {
        if(firstTime) firstLoginFlag = true

        btnUpdate.setOnClickListener {
            if (firstTime) {
                showMessage("Se lo está redirigiendo a la pantalla de registro corporal")

                lifecycleScope.launch(Dispatchers.Main) {
                    delay(1000)
                    goToBodyRegistry()
                }

            } else {
                updatePacienteInfo()
            }
        }
    }

    private fun getFechaNacimiento(fechNac: Timestamp?): String {
        return if (fechNac != null) {
            calendar.timeInMillis = fechNac.seconds * 1000
            val fecha = Date(calendar.timeInMillis)
            fecha.stringFromDate()
        } else {
            CAMPO_NO_ASIGNADO
        }
    }

    private fun setFechaNacimiento(): Timestamp? {
        if (etFechaNacimiento.text.toString() != CAMPO_NO_ASIGNADO) {
            val fechaObtenida = etFechaNacimiento.text.toString().dateFromString()

            if (fechaObtenida != null) {
                return Timestamp(fechaObtenida)
            }
        }

        return null
    }

    private fun disableRegistroCorporalFields() {
        etPeso.isEnabled = false
        etMedidaCintura.isEnabled = false
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

    override fun goToHomeView() {
        Intent(this@PerfilPacienteActivity, PaginaPrincipalActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun goToDailyRegistryView() {
        Intent(this@PerfilPacienteActivity, DailyRegistryActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun goToProgressView() {
        Intent(this@PerfilPacienteActivity, RegistroCorporalActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun goToProfileView() {
        refreshActivity()
    }

    override fun goToLoginView() {
        lifecycleScope.launch(Dispatchers.IO) {
            userLogout()
        }

        Intent(this, LoginActivity::class.java).apply {
            flags = FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
    }

    override fun refreshActivity() {
        finish()
        overridePendingTransition(0, 0)
        Intent(this@PerfilPacienteActivity, this::class.java).apply {
            startActivity(this)
        }
        overridePendingTransition(0, 0)
    }

    private fun goToBodyRegistry() {
        val patient = buildPacienteFromForm()

        Intent(this, RegistroCorporalActivity::class.java).apply {
            putExtra(FIRST_LOGIN, true)
            putExtra(USER_TO_UPDATE, patient)
            startActivity(this)
        }
    }

    private suspend fun userLogout() {
        SessionManager.saveLoggedUser(null)
    }

    private fun showMessage(msg: String) {
        UIManager.showMessageLong(this, msg)
    }

    override fun onBackPressed() {
        bottomNavBar.selectedItemId = R.id.home

        if(firstLoginFlag){
            goToLoginView()
        }

        super.onBackPressed()
    }
}