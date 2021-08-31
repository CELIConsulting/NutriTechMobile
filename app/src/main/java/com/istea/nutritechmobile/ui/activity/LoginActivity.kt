package com.istea.nutritechmobile.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.istea.nutritechmobile.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NutriTechMobile)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}