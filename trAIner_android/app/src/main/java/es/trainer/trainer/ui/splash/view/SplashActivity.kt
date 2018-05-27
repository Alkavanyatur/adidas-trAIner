package es.trainer.trainer.ui.splash.view

import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import es.trainer.trainer.ui.main.MainActivity

import es.trainer.trainer.R
import es.trainer.trainer.data.repository.signin.SignInRepository
import es.trainer.trainer.ui.signin.view.SignInActivity
import es.trainer.trainer.ui.splash.contract.SplashControlUserContract
import es.trainer.trainer.ui.splash.contract.SplashControlUserPresenter
import es.trainer.trainer.data.utils.ControlNetwork

class SplashActivity : AppCompatActivity(), SplashControlUserContract.ViewSplash {

    private var presenterControlUser: SplashControlUserContract.PresenterSplash? = null

    var controlNetwork: ControlNetwork? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        controlNetwork = ControlNetwork(this)

        if (controlNetwork!!.isNetWorkAvaible()) {
            presenterControlUser = SplashControlUserPresenter(SignInRepository())
        }
        else{
            errorNetWork()
            showSignInActivity()
        }


        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


    }

    override fun onStart() {
        super.onStart()

        if (controlNetwork!!.isNetWorkAvaible()) {
            presenterControlUser!!.controlUserFirebase(this)
        }
        else{
            errorNetWork()
            showSignInActivity()
        }

    }

    override fun setPresenter(presenter: SplashControlUserContract.PresenterSplash) {
        presenterControlUser = presenter
    }

    override fun showMainScreen() {
        val i = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun showLoginError() {
        showSignInActivity()
    }

    fun showSignInActivity(){
        val i = Intent(this@SplashActivity, SignInActivity::class.java)
        startActivity(i)
        finish()
    }

    fun errorNetWork(){
        Toast.makeText(this, getString(R.string.error_network), Toast.LENGTH_LONG).show()
    }
}
