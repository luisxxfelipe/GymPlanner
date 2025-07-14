package com.dispositivosmoveis.gymplanner.utils

import android.content.Context

object SessaoManager {
    private const val PREF_NAME = "gymplanner_pref"
    private const val KEY_USER_ID = "usuario_id"

    fun salvarUsuarioId(context: Context, id: Long) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putLong(KEY_USER_ID, id).apply()
    }

    fun obterUsuarioId(context: Context): Long {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(KEY_USER_ID, -1)
    }

    fun limparSessao(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_USER_ID).apply()
    }
}
