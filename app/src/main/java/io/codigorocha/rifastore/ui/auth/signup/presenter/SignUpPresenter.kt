package io.codigorocha.rifastore.ui.auth.signup.presenter

import io.codigorocha.rifastore.domain.signup.SignUpInteractor
import io.codigorocha.rifastore.ui.auth.signup.SignUpContract
import javax.inject.Inject

class SignUpPresenter @Inject constructor(private val signUpInteractor: SignUpInteractor):
    SignUpContract.SignUpPresenter {

    var view: SignUpContract.SignUpView? = null

    override fun attachView(view: SignUpContract.SignUpView) {
        this.view = view
    }

    override fun dettachView() {
        view = null
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun checkEmptyFields(fullName: String, email: String, password: String): Boolean {
        return fullName.isEmpty() || email.isEmpty() || password.isEmpty()
    }


    override fun signUpWithEmailAndPassword(fullName: String, email: String, password: String) {
        view?.showProgressBar()
        signUpInteractor.signUp(fullName, email, password, object: SignUpInteractor.SignUpCallback {

            override fun onSignUpSucces() {
                view?.navigateToSignIn()
                view?.hideProgressBar()
            }

            override fun onSignUpFailure(error: String) {
                view?.showError(error)
                view?.hideProgressBar()
            }

        })
    }
}