package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat.startActivity
import com.istea.nutritechmobile.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(
            {
                launchlogin()
                finish()
            }, 2500
        )
}

private fun launchlogin() {
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
}
}