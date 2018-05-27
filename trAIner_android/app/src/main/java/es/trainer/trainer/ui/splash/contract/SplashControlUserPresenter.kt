package es.trainer.trainer.ui.splash.contract

import es.trainer.trainer.data.repository.signin.SignInDataSource.OnControlUserRequestListener
import es.trainer.trainer.data.repository.signin.SignInRepository
import es.trainer.trainer.ui.splash.contract.SplashControlUserContract.ViewSplash

class SplashControlUserPresenter(val repository: SignInRepository):SplashControlUserContract.PresenterSplash {

    private var viewSplash: ViewSplash? = null

    private var signInRegisterListenerDataSource: OnControlUserRequestListener = object : OnControlUserRequestListener {
        override fun onControlUserSuccess() {
            viewSplash!!.showMainScreen()
        }

        override fun onControlUserError() {
            viewSplash!!.showLoginError()
        }

    }

    override fun controlUserFirebase(view: ViewSplash) {
        viewSplash = view

        repository.controlUserWithFirebase(signInRegisterListenerDataSource)
    }
}