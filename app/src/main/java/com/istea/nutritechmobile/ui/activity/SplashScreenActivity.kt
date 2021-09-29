package com.istea.nutritechmobile.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
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

        callSessionManager()

        Handler().postDelayed(
            {
                launchlogin()
                finish()
            }, 2500
        )
}

    private fun callSessionManager() {
        GlobalScope.launch(Dispatchers.IO){
            Log.i(TAG_ACTIVITY, "Coroutine to get Shared Preferences starts here!")
            SessionManager.getPreferences(this@SplashScreenActivity.applicationContext)
            Log.i(TAG_ACTIVITY, "Coroutine to get Shared Preferences ends here!")
        }
    }

    private fun launchlogin() {
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
}
}