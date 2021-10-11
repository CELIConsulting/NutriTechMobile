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

        GlobalScope.launch(Dispatchers.IO) {
            invokeSessionManager()
        }

        lifecycleScope.launch(Dispatchers.Main) {
            delay(2500L)
            launchlogin()
            Log.i(TAG_ACTIVITY, "Finishing activity")
            finish()
        }
    

    }

    private suspend fun invokeSessionManager() {
        Log.i(TAG_ACTIVITY, "Invoking Session Manager")
        SessionManager.getPreferences(this@SplashScreenActivity.applicationContext)
    }

    private suspend fun launchlogin() {
        Log.i(TAG_ACTIVITY, "Launching login")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}