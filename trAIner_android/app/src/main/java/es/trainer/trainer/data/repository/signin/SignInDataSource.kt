package es.trainer.trainer.data.repository.signin

interface SignInDataSource {

    interface OnLoginRequestListener{
        fun onLoginSuccess()
        fun onLoginError(error_login: Int)

    }

    interface OnRegisterRequestListener{
        fun onRegisterSuccess()
        fun onRegisterError(register_error: Int)
    }

    interface OnControlUserRequestListener{
        fun onControlUserSuccess()
        fun onControlUserError()
    }

    fun loginWithFirebase(loginRequestListener: SignInDataSource.OnLoginRequestListener, emailStr: String, passwordStr: String)
    fun registerWithFirebase(registerRequestListener: OnRegisterRequestListener, emailStr: String, passwordStr: String, identifierStr: String)
    fun controlUserWithFirebase(controlUserRequestListener: OnControlUserRequestListener)
}