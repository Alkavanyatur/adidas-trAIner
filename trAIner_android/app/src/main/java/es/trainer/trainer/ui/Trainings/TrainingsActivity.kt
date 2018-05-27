package es.trainer.trainer.ui.Trainings

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import es.trainer.trainer.R

import kotlinx.android.synthetic.main.activity_trainings.*
import kotlinx.android.synthetic.main.card_training.*
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class TrainingsActivity : AppCompatActivity() {

    val CONNECTON_TIMEOUT_MILLISECONDS = 60000
    var training: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainings)
        setSupportActionBar(toolbar)

        training = intent.getStringExtra("training")

        if (training.contentEquals("")) {
            setNewTraining()
        }

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Get a new training", Snackbar.LENGTH_LONG)
                    .setAction("Get New", null).show()
        }
    }


    fun setNewTraining() {
        val url = "https://227c3092.ngrok.io/sessionDone"
        SetNewTrainingAsyncTask().execute(url, training)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, TrainingsActivity::class.java)
            return intent
        }
    }


    inner class SetNewTrainingAsyncTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            // Before doInBackground
        }

        override fun doInBackground(vararg urls: String?): String {
            var urlConnection: HttpURLConnection? = null

            val url = URL(urls[0])
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            connection.setRequestProperty("Accept", "*/*")

            connection.doOutput = true
            val writer = BufferedWriter(OutputStreamWriter(connection.outputStream))
            writer.write(urls[1])
            writer.close()

            connection.connect()

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
        }

        override fun onPostExecute(result: String?) {
            // Done
        }
    }
}
