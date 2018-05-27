package es.trainer.trainer.ui.Trainings

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import es.trainer.trainer.R
import es.trainer.trainer.data.model.Session

import kotlinx.android.synthetic.main.activity_actual_training.*
import kotlinx.android.synthetic.main.card_training.*
import kotlinx.android.synthetic.main.content_user.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ActualTrainingActivity : AppCompatActivity() {

    val CONNECTON_TIMEOUT_MILLISECONDS = 60000

    private var rating = "None"
    private var fc = 100
    private lateinit var training : JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actual_training)
        setSupportActionBar(toolbar)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fab.setOnClickListener { view ->
            finishTraining()
        }
        val response = getNewTraining()
    }


    fun finishTraining(){
        val array = arrayOf("1/5... bad","2/5 ... not good","3/5 ... you can do it better","4/5 ... very good","5/5 ... Perfect!")
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_finish_trainings))
                .setSingleChoiceItems(array,-1,{_,which->
                    // Get the dialog selected item
                    rating = array[which]
                    fc = (fc * 0.95).toInt()
                    goToTrainings(rating)
                })
                .setNegativeButton(getString(R.string.button_ko_finish_trainings), DialogInterface.OnClickListener { dialog, which -> Log.d("MainActivity", "Aborting mission...") })
                .create().show()
    }

    fun goToTrainings(rating: String){
        val intent = TrainingsActivity.newIntent(this)
        training.put("rating",rating)
        training.put("FC","120")
        intent.putExtra("training", training.toString())
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {

    }

    fun getNewTraining() {
        val url = "http://227c3092.ngrok.io/session/BMJn5ELzhBWZlf6bCWY1swg6BXx1"
        GetNewTrainingAsyncTask().execute(url)
    }


    inner class GetNewTrainingAsyncTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            // Before doInBackground
        }

        override fun doInBackground(vararg urls: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val url = URL(urls[0])

                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = CONNECTON_TIMEOUT_MILLISECONDS
                urlConnection.readTimeout = CONNECTON_TIMEOUT_MILLISECONDS

                var inString = streamToString(urlConnection.inputStream)

                publishProgress(inString)
            } catch (ex: Exception) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                var json = JSONObject(values[0])

                val user = json.getJSONObject("user")
                val conditions = json.getJSONObject("conditions")
                val exercise = json.getJSONObject("exercise")
                val work = exercise.getJSONObject("work")
                val volume = work.getJSONObject("volume")

                value_aerobic.text = volume.get("aerobicLoad").toString()
                value_anaerobic.text = volume.get("anaerobicLoad").toString()
                value_intensity.text = work.get("intensity").toString()

                value_date.text = conditions.get("dateTime").toString()
                value_description.text = work.get("description").toString()

                value_mood.text = conditions.get("feeling").toString()
                value_place.text = conditions.get("place").toString()
                value_weather.text = conditions.get("weather").toString()

                training = json
                fc = work.getInt("intensity")

            } catch (ex: Exception) {

            }
        }

        override fun onPostExecute(result: String?) {
            // Done
        }
    }

    fun streamToString(inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {

        }

        return result
    }


    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, ActualTrainingActivity::class.java)
            return intent
        }
    }

}
