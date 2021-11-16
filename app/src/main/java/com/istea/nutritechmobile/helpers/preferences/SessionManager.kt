package com.istea.nutritechmobile.helpers.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.istea.nutritechmobile.data.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

const val TAG_ACTIVITY = "SessionManager"

class SessionManager {
    companion object {
        private const val SESSION_PREFERENCES = "SessionPreferences"
        private const val LOGGED_USER = "CurrentLoggedUser"
        private const val DEFAULT_USER = ""
        private lateinit var sharedPreferences: SharedPreferences
        private val gson: Gson by lazy {
            Gson()
        }

        ///Getting shared preferences || ASYNC
        suspend fun getPreferences(context: Context) {
            Log.i(TAG_ACTIVITY, "Getting shared preferences....")
            sharedPreferences = withContext(Dispatchers.IO) {
                context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE)
            }
        }

        ///Saving user in shared preferences || ASYNC
        suspend fun saveLoggedUser(user: UserResponse?): Boolean {
            if (user != null) {
                val userGSON = gson.toJson(user)
                sharedPreferences.edit {
                    putString(LOGGED_USER, userGSON)
                    apply()
                    return true
                }
            } else {
                sharedPreferences.edit {
                    remove(LOGGED_USER)
                }
                return false
            }
            return false
        }


        ///Getting user in shared preferences || ASYNC
        suspend fun getLoggedUser(): UserResponse? {
            val userGSON = sharedPreferences.getString(LOGGED_USER, DEFAULT_USER)

            return if (userGSON.isNullOrEmpty()) {
                null
            } else {
                gson.fromJson(userGSON, UserResponse::class.java)
            }
        }


    }
}