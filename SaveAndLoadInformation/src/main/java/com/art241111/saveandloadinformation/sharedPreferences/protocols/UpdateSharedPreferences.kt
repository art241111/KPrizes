package com.art241111.saveandloadinformation.sharedPreferences.protocols

interface UpdateSharedPreferences {
    fun update(oldValue: String, newValue: String, preferencesKey: String): String
}
