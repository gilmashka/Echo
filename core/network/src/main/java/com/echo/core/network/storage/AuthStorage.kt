package com.echo.core.network.storage

import android.content.Context
import android.util.Log
import jakarta.inject.Inject

interface AuthStorage {

    fun saveSession(nickname: String, password: String, userId: Long)
    fun getCredentials(): Pair<String, String>?
    fun getUserId(): Long
    fun clear()

    fun isDarkTheme(): Boolean

    fun setDarkTheme(dark: Boolean)
}

class SharedPreferencesAuthStorage @Inject constructor(
    private val context: Context
) : AuthStorage {

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    override fun saveSession(nickname: String, pass: String, userId: Long) {
        Log.d("AuthStorage", "Saving: $nickname, pass length: ${pass.length}")
        prefs.edit()
            .putString("nick", nickname)
            .putString("pass", pass)
            .putLong("uid", userId)
            .apply()
    }

    override fun getCredentials(): Pair<String, String>? {
        val nick = prefs.getString("nick", null) ?: return null
        val pass = prefs.getString("pass", null) ?: return null
        return nick to pass
    }

    override fun getUserId() = prefs.getLong("uid", -1L)

    override fun clear() {
        prefs.edit().clear().apply()
    }

    override fun isDarkTheme(): Boolean = prefs.getBoolean("dark_theme", false)

    override fun setDarkTheme(dark: Boolean) {
        prefs.edit().putBoolean("dark_theme", dark).apply()
    }
}
