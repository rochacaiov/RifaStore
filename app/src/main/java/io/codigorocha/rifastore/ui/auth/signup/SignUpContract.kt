package io.codigorocha.rifastore.ui.auth.signup

interface SignUpContract {

    interface SignUpView {
        fun showError(error: String)
        fun showProgressBar()
        fun hideProgressBar()
        fun signUp()
        fun navigateToSignIn()
    }

    interface SignUpPresenter {
        fun attachView(view: SignUpView)
        fun dettachView()
        fun isViewAttached(): Boolean
        fun checkEmptyFields(fullName: String, email: String, password: String): Boolean

        fun signUpWithEmailAndPassword(fullName: String, email: String, password: String)
    }

}