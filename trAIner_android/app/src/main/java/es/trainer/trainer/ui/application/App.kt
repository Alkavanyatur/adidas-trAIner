package es.trainer.trainer.ui.application

import android.app.Application
import es.trainer.trainer.data.utils.PreferencesHelper

class App: Application() {
    companion object {
        var prefs: PreferencesHelper? = null
    }

    override fun onCreate() {
        prefs = PreferencesHelper(applicationContext)
        super.onCreate()

    }
}