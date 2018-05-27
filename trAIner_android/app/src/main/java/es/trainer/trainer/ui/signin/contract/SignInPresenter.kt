package es.trainer.trainer.ui.signin.contract

import es.trainer.trainer.data.repository.signin.SignInDataSource.*
import es.trainer.trainer.data.repository.signin.SignInRepository
import es.trainer.trainer.ui.signin.contract.SignInContract.*

class SignInPresenter(val repository: SignInRepository) : SignInContract.PresenterLogin, SignInContract.PresenterRegister {

    /**
     * Variables de SignContract para devolver las respuestas a sus vistas fragment
     */
    private var viewRegister: ViewRegister? = null
    private var viewLogin: ViewLogin? = null


    private var signInRegisterListenerDataSource: OnRegisterRequestListener = object : OnRegisterRequestListener {
        override fun onRegisterSuccess() {
            viewRegister!!.showMainScreen()
            viewRegister!!.showLoading(false)
        }

        override fun onRegisterError(register_error: Int) {
            viewRegister!!.showRegisterError(register_error)
            viewRegister!!.showLoading(false)
        }

    }
    private var signInLoginListenerDataSource: OnLoginRequestListener = object : OnLoginRequestListener {
        override fun onLoginSuccess() {
           viewLogin!!.showMainScreen()
            viewLogin!!.showLoading(false)
        }

        override fun onLoginError(error_login: Int) {
            viewLogin!!.showLoginError(error_login)
            viewLogin!!.showLoading(false)
        }
    }


    override fun logInWithFirebase(view: ViewLogin, emailStr: String, passwordStr: String) {
        viewLogin = view
        viewLogin!!.showLoading(true)
        repository.loginWithFirebase(signInLoginListenerDataSource, emailStr, passwordStr)
    }

    override fun registerWithFirebase(view: ViewRegister, emailStr: String, passwordStr: String, identifierStr: String) {
       viewRegister = view
        viewRegister!!.showLoading(true)
        repository.registerWithFirebase(signInRegisterListenerDataSource, emailStr, passwordStr, identifierStr)
    }


}