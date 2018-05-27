package es.trainer.trainer.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import es.trainer.trainer.R
import es.trainer.trainer.ui.application.App

import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.content_user.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolbar)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadUserInfo()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun loadUserInfo(){
        user_profile_name.text = App.prefs!!.name
        text_date_birth.text =  "Date of birth:   " + App.prefs!!.dateBird
        text_time_to_notify.text =  "Time to next training after notify it: 30 minutes"
        text_vo2.text = "V02:   " + App.prefs!!.vO2.toString()
        text_weight.text = "Weight:   " + App.prefs!!.weight.toString()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, UserActivity::class.java)
            return intent
        }
    }

}
