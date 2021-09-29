package com.istea.nutritechmobile.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.istea.nutritechmobile.R
import com.istea.nutritechmobile.helpers.preferences.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG_ACTIVITY = "SplashScreenActivity"

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch(Dispatchers.Main) {
            invokeSessionManager()
            delay(1000L)
            launchlogin()
            finish()
        }
    }

    private suspend fun invokeSessionManager() {
        SessionManager.getPreferences(this@SplashScreenActivity.applicationContext)
    }

    private fun launchlogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}