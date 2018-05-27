package es.trainer.trainer.data.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val prefs_name = "es.trainer.trainer.data.utils.sharedpreferences"

    private val user_date_birth = "user_date_birth"
    private val user_deltaN = "user_deltaN"
    private val user_name = "user_name"
    private val user_vO2 = "user_vO2"
    private val user_weight = "user_weight"
    private val user_identifier = "user_identifier"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefs_name, 0)

    var dateBird: String
        get() = prefs.getString(user_date_birth, "")
        set(value) = prefs.edit().putString(user_date_birth, value).apply()

    var deltaN: Float
        get() = prefs.getFloat(user_deltaN, 0F)
        set(value) = prefs.edit().putFloat(user_deltaN, value).apply()

    var name: String
        get() = prefs.getString(user_name, "")
        set(value) = prefs.edit().putString(user_name, value).apply()

    var vO2: Float
        get() = prefs.getFloat(user_vO2, 0F)
        set(value) = prefs.edit().putFloat(user_vO2, value).apply()

    var weight: Float
        get() = prefs.getFloat(user_weight, 0F)
        set(value) = prefs.edit().putFloat(user_weight, value).apply()

    var identifier: String
        get() = prefs.getString(user_identifier, "")
        set(value) = prefs.edit().putString(user_identifier, value).apply()

    fun updatePreferences(_dateBird: String,_deltaN: Float,_name: String,_vO2: Float,_weight: Float){
        dateBird = _dateBird
        deltaN = _deltaN
        name = _name
        vO2 = _vO2
        weight = _weight
    }
}