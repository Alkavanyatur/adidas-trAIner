package es.trainer.trainer.ui.signin.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import es.trainer.trainer.ui.main.MainActivity
import es.trainer.trainer.R
import es.trainer.trainer.data.repository.signin.SignInRepository
import es.trainer.trainer.ui.signin.contract.SignInContract
import es.trainer.trainer.ui.signin.contract.SignInPresenter
import kotlinx.android.synthetic.main.component_loading.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), SignInContract.ViewLogin {

    private var presenterLogin: SignInContract.PresenterLogin? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideLoading() //Ocultamos el loading

        presenterLogin = SignInPresenter(SignInRepository())

        password_login.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        button_login.setOnClickListener { attemptLogin() }
        // Inflate the layout for this fragment
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {

        // Reset errors.
        email_login.error = null
        password_login.error = null

        // Store values at the time of the login attempt.
        val emailStr = email_login.text.toString()
        val passwordStr = password_login.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password_login.error = getString(R.string.error_invalid_password)
            focusView = password_login
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email_login.error = getString(R.string.error_field_required)
            focusView = email_login
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email_login.error = getString(R.string.error_invalid_email)
            focusView = email_login
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {

            presenterLogin?.logInWithFirebase(this, emailStr, passwordStr)
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true)
            // mAuthTask = UserLoginTask(emailStr, passwordStr)
            // mAuthTask!!.execute(null as Void?)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }


    override fun setPresenter(presenter: SignInContract.PresenterLogin) {
        presenterLogin = presenter
    }

    override fun showLoading(show: Boolean) {
        component_loading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun hideLoading(){
        component_loading.visibility = View.GONE
    }

    override fun showMainScreen() {
        val intent = Intent(this.context, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this.context, getString(R.string.ok_login), Toast.LENGTH_LONG).show()
    }

    override fun showLoginError(login_error: Int) {
        Toast.makeText(this.context, getString(login_error), Toast.LENGTH_LONG).show()
    }

}
