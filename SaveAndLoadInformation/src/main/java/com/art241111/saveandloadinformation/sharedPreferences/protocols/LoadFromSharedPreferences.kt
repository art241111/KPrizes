package com.art241111.saveandloadinformation.sharedPreferences.protocols

interface LoadFromSharedPreferences {
    fun load(preferencesKey: String, defaultValue: String = ""): String
}
