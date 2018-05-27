package es.trainer.trainer.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.WindowManager
import es.trainer.trainer.ui.nutrition.NutritionActivity
import es.trainer.trainer.ui.objectives.ObjectivesActivity
import es.trainer.trainer.R
import es.trainer.trainer.ui.Trainings.TrainingsActivity
import es.trainer.trainer.ui.user.UserActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.util.Log
import es.trainer.trainer.ui.Trainings.ActualTrainingActivity


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fab.setOnClickListener { view ->
            newTraining()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_user -> {
                val intent = UserActivity.newIntent(this)
                startActivity(intent)
            }
            R.id.nav_training -> {
                val intent = TrainingsActivity.newIntent(this)
                startActivity(intent)
            }
            R.id.nav_eating -> {
                val intent = NutritionActivity.newIntent(this)
                startActivity(intent)
            }
            R.id.nav_goals -> {
                val intent = ObjectivesActivity.newIntent(this)
                startActivity(intent)
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun newTraining(){
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_new_trainings))
                .setMessage(getString(R.string.text_new_trainings))
                .setPositiveButton(getString(R.string.button_ok_new_trainings), DialogInterface.OnClickListener { dialog, which -> goToNewTraining() })
                .setNegativeButton(getString(R.string.button_ko_new_trainings), DialogInterface.OnClickListener { dialog, which -> Log.d("MainActivity", "Aborting mission...") })
                .show()
    }

    fun goToNewTraining(){
        val intent = ActualTrainingActivity.newIntent(this)
        startActivity(intent)
    }
}
