package com.dream_engineering.cloud_budget_tracker.utils

// SharedPreferencesManager.kt

import android.content.Context

object SharedPreferencesManager {
    private const val PREFS_NAME = "MyAppPrefs"
    private const val KEY_USER_EMAIL = "user_email"

    fun saveUserEmail(context: Context, userEmail: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(KEY_USER_EMAIL, userEmail)
        editor.apply()
    }

    fun getUserEmail(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    fun clearUserEmail(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(KEY_USER_EMAIL)
        editor.apply()
    }
}
