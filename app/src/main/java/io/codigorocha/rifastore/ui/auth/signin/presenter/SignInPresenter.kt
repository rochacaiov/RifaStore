package io.codigorocha.rifastore.ui.auth.signin.presenter

import io.codigorocha.rifastore.domain.signin.SignInInteractor
import io.codigorocha.rifastore.ui.auth.signin.SignInContract
import io.codigorocha.rifastore.ui.auth.signin.exceptions.FirebaseSignInException
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SignInPresenter @Inject constructor(private val signInInteractor: SignInInteractor)
    : SignInContract.SignInPresenter, CoroutineScope {

    var view: SignInContract.SignInView? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun attachView(view: SignInContract.SignInView) {
        this.view = view
    }

    override fun dettachView() {
        view = null
    }

    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun signInUserWithEmailAndPassword(email: String, password: String) {
        launch {
            view?.showProgressBar()

            try {
                signInInteractor.signInWithEmailAndPassword(email, password)

                if (isViewAttached()) {
                    view?.hideProgressBar()
                    view?.navigateToMain()
                }
            } catch (e:FirebaseSignInException) {

                if (isViewAttached()) {
                    view?.showError(e.message)
                    view?.hideProgressBar()
                }

            }

        }
    }

    override fun checkEmptyFields(email: String, password: String): Boolean {
        return email.isEmpty() || password.isEmpty()
    }
}