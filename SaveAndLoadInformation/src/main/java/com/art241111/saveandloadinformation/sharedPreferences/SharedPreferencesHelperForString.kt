package com.art241111.saveandloadinformation.sharedPreferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.art241111.saveandloadinformation.sharedPreferences.protocols.LoadFromSharedPreferences
import com.art241111.saveandloadinformation.sharedPreferences.protocols.SaveSharedPreferences
import com.art241111.saveandloadinformation.sharedPreferences.protocols.UpdateSharedPreferences

class SharedPreferencesHelperForString(activity: Activity, sharedPreferencesName: String) :
    SaveSharedPreferences, LoadFromSharedPreferences, UpdateSharedPreferences {

    private val preferences: SharedPreferences =
        activity.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    /**
     * Берется значение из sharedPreferences.
     * Если такого значения нет, то возвращается defaultValue
     *
     * @param preferencesKey - key for sharedPreferences
     * @param defaultValue - дефолтное значение, которое возвратиться, если
     *                       с таким ключом значений нет
     *
     * @return Если существет значение в SharedPreferences, то возвращается оно,
     * иначе возвращается defaultValue
     */
    override fun load(preferencesKey: String, defaultValue: String): String =
        if (preferences.contains(preferencesKey)) {
            preferences.getString(preferencesKey, defaultValue).toString()
        } else {
            defaultValue
        }

    override fun save(preferencesKey: String, newValue: String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(preferencesKey, newValue)
        editor.apply()
    }

    override fun update(oldValue: String, newValue: String, preferencesKey: String): String {
        return if (oldValue != newValue) {
            save(preferencesKey, newValue)
            newValue
        } else {
            oldValue
        }
    }
}
