package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.firebase.Timestamp
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.data.UserResponse
import com.istea.nutritechmobile.helpers.extensions.stringFromDate
import com.istea.nutritechmobile.helpers.preferences.SessionManager
import com.istea.nutritechmobile.presenter.PerfilPacientePresenterImp
import com.istea.nutritechmobile.presenter.interfaces.IPerfilPacientePresenter
import com.istea.nutritechmobile.ui.interfaces.IPerfilPacienteView
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import java.util.*

private const val campoNoAsignado = "No asignado"

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
    private var calendar: GregorianCalendar = GregorianCalendar()
    private val perfilPresenter: IPerfilPacientePresenter by lazy {
        PerfilPacientePresenterImp(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_scroll)
        setupUI()
    }

    override fun onResume() {
        lifecycleScope.launch(Dispatchers.Main) {
            perfilPresenter.getPaciente()
        }

        super.onResume()
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

        btnUpdate.setOnClickListener {
            updatePacienteInfo()
        }
    }

    override fun showPacienteInfo(paciente: UserResponse) {
        etNombre.setText(if (!paciente.Nombre.isEmpty()) paciente.Nombre else campoNoAsignado)
        etApellido.setText(if (!paciente.Apellido.isEmpty()) paciente.Apellido else campoNoAsignado)
        etMail.setText(if (!paciente.Email.isEmpty()) paciente.Email else campoNoAsignado)
        etFechaNacimiento.setText(getFechaNacimiento(paciente.FechaNacimiento))
        etTelefono.setText(if (!paciente.Telefono.isEmpty()) paciente.Telefono else campoNoAsignado)
        etAltura.setText(if (paciente.Altura != null && paciente.Altura!! > 0) paciente.Altura.toString() else campoNoAsignado)
        etPeso.setText(if (paciente.Peso != null  && paciente.Peso!! > 0) paciente.Peso.toString() else campoNoAsignado)
        etMedidaCintura.setText(if (paciente.MedidaCintura != null && paciente.MedidaCintura!! > 0) paciente.MedidaCintura.toString() else campoNoAsignado)
        etTipoAlimentacion.setText(if (!paciente.TipoAlimentacion.isEmpty()) paciente.TipoAlimentacion else campoNoAsignado)
    }

    private fun getFechaNacimiento(fechNac: Timestamp?): String {
        if (fechNac != null) {
            calendar.timeInMillis = fechNac.seconds * 1000
            val fecha = Date(calendar.timeInMillis)
            return fecha.stringFromDate()
        } else {
            return campoNoAsignado
        }
    }

    override fun updatePacienteInfo() {
        //Crear nuevo paciente en base al form (Mapear unicamente la info editable)
        val paciente = buildPacienteFromForm()

        //Actualizar datos del usuario
        lifecycleScope.launch(Dispatchers.Main) {
            perfilPresenter.updatePaciente(paciente)
        }

    }

    override fun goBackToLogin() {
        lifecycleScope.launch(Dispatchers.IO) {
            SessionManager.saveLoggedUser(null)
        }

        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun buildPacienteFromForm(): UserResponse {
        val updatedUser: UserResponse = UserResponse()

        updatedUser.Nombre = etNombre.text.toString()
        updatedUser.Apellido = etApellido.text.toString()
        updatedUser.Telefono = etTelefono.text.toString()
        updatedUser.Email = etMail.text.toString()
        //Corregir fechas - Utilizar datepicker
        updatedUser.Altura = etAltura.text.toString().toFloat()
        updatedUser.Peso = etPeso.text.toString().toFloat()
        updatedUser.MedidaCintura = etMedidaCintura.text.toString().toFloat()
        updatedUser.TipoAlimentacion = etTipoAlimentacion.text.toString()

        return updatedUser;
    }
}