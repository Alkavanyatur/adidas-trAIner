package es.trainer.trainer.ui.splash.contract

import es.trainer.trainer.ui.base.BasePresenter
import es.trainer.trainer.ui.base.BaseView

interface SplashControlUserContract {

    interface ViewSplash : BaseView<PresenterSplash> {
        fun showMainScreen()
        fun showLoginError()
    }

    interface PresenterSplash : BasePresenter {
        fun controlUserFirebase(view: ViewSplash)
    }
}