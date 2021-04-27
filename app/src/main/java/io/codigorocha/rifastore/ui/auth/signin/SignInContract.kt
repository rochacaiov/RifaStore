package io.codigorocha.rifastore.ui.auth.signin

import android.widget.TextView

interface SignInContract {

    interface SignInView {
        fun showError(error: String?)
        fun showProgressBar()
        fun hideProgressBar()
        fun setSpannable(text: String, color: Int, start: Int, end: Int, textView: TextView)
        fun setupGoogleSignIn()
        fun signInWithEmailAndPassword()
        fun signInWithGoogle()
        fun navigateToMain()
        fun navigateToRegister()
    }

    interface SignInPresenter {
        fun attachView(view: SignInView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached(): Boolean
        fun signInUserWithEmailAndPassword(email: String, password: String)
        fun checkEmptyFields(email: String, password: String): Boolean
    }

}