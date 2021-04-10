package com.art241111.saveandloadinformation.sharedPreferences.protocols

interface SaveSharedPreferences {
    fun save(preferencesKey: String, newValue: String)
}
