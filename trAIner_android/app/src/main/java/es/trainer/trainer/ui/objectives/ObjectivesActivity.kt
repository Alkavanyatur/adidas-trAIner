package es.trainer.trainer.ui.objectives

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import es.trainer.trainer.R

import kotlinx.android.synthetic.main.activity_objectives.*

class ObjectivesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objectives)
        setSupportActionBar(toolbar)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Get a new training", Snackbar.LENGTH_LONG)
                    .setAction("Get New", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, ObjectivesActivity::class.java)
            return intent
        }
    }

}
