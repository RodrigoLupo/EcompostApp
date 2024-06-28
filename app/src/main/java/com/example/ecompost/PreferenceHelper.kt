package com.example.ecompost

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private const val PREFERENCE_NAME = "EcompostPreferences"
    private const val ACCESS_TOKEN = "AccessToken"
    private const val REFRESH_TOKEN = "RefreshToken"
    private const val IS_LOGGED_IN = "IsLoggedIn"
    private const val USER_ID = "user_id"
    private const val USER_NAME = "user_name"

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun saveAccessToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(ACCESS_TOKEN, token)
        editor.apply()
    }

    fun getAccessToken(context: Context): String? {
        return getSharedPreferences(context).getString(ACCESS_TOKEN, null)
    }

    fun saveRefreshToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(REFRESH_TOKEN, token)
        editor.apply()
    }


    fun setLoggedIn(context: Context, loggedIn: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(IS_LOGGED_IN, loggedIn)
        editor.apply()
    }

}
