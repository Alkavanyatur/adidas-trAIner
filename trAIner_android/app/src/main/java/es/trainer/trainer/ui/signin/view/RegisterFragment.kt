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
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), SignInContract.ViewRegister {

    private var presenterRegister: SignInContract.PresenterRegister? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideLoading() //Ocultamos el loadings

        presenterRegister = SignInPresenter(SignInRepository())

        password_register.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptRegister()
                return@OnEditorActionListener true
            }
            false
        })

        button_register.setOnClickListener { attemptRegister() }
        // Inflate the layout for this fragment
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptRegister() {

        // Reset errors.
        email_register.error = null
        password_register.error = null

        // Store values at the time of the login attempt.
        val emailStr = email_register.text.toString()
        val passwordStr = password_register.text.toString()
        val identifierStr = identifier_register.text.toString()

        var cancel = false
        var focusView: View? = null

        //Check for a valid identifier, if the user entered one
        if (TextUtils.isEmpty(identifierStr))
        {
            identifier_register.error = getString(R.string.error_field_required)
            focusView = identifier_register
            cancel = true
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password_register.error = getString(R.string.error_invalid_password)
            focusView = password_register
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email_register.error = getString(R.string.error_field_required)
            focusView = email_register
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email_register.error = getString(R.string.error_invalid_email)
            focusView = email_register
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {

            presenterRegister?.registerWithFirebase(this, emailStr, passwordStr, identifierStr)

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

    override fun showLoading(show: Boolean) {
        component_loading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun hideLoading(){
        component_loading.visibility = View.GONE
    }

    override fun showMainScreen() {
        val intent = Intent(this.context, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this.context, getString(R.string.ok_register), Toast.LENGTH_LONG).show()
    }

    override fun showRegisterError(register_error: Int) {
        Toast.makeText(this.context, getString(register_error), Toast.LENGTH_LONG).show()
    }

    override fun setPresenter(presenter: SignInContract.PresenterRegister) {
        presenterRegister = presenter
    }


}
