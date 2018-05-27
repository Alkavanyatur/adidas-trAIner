package es.trainer.trainer.ui.signin.contract

import es.trainer.trainer.data.repository.signin.SignInDataSource
import es.trainer.trainer.ui.base.BasePresenter
import es.trainer.trainer.ui.base.BaseView

interface SignInContract {

    interface ViewLogin : BaseView<PresenterLogin> {
          fun showLoading(show: Boolean)
          fun showMainScreen()
          fun showLoginError(login_error: Int)
    }

    interface PresenterLogin : BasePresenter {
        fun logInWithFirebase(view: ViewLogin, emailStr: String, passwordStr: String)
    }

    interface ViewRegister : BaseView<PresenterRegister> {
          fun showLoading(show: Boolean)
          fun showMainScreen()
          fun showRegisterError(register_error: Int)
    }

    interface PresenterRegister : BasePresenter {
        fun registerWithFirebase(view: ViewRegister, emailStr: String, passwordStr: String, identifierStr: String)
    }

}