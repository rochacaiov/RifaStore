package io.codigorocha.rifastore.ui.auth.signup.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.codigorocha.rifastore.BaseActivity
import io.codigorocha.rifastore.R
import io.codigorocha.rifastore.RifaStore
import io.codigorocha.rifastore.ui.auth.signin.view.SignInActivity
import io.codigorocha.rifastore.ui.main.view.MainActivity
import io.codigorocha.rifastore.ui.auth.signup.SignUpContract
import io.codigorocha.rifastore.ui.auth.signup.presenter.SignUpPresenter
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUpActivity : BaseActivity(), SignUpContract.SignUpView {

    @Inject
    lateinit var presenter: SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as RifaStore).getAppComponent()?.inject(this)
        presenter.attachView(this)

        button_signUp.setOnClickListener {
            signUp()
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_sign_up
    }

    override fun showError(error: String) {
        toast(this, error)
    }

    override fun showProgressBar() {
        progressBar_signUp.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar_signUp.visibility = View.GONE
    }

    override fun signUp() {
        val fullName = input_fullName.text.toString().trim()
        val email = input_email.text.toString().trim()
        val password = input_password.text.toString().trim()

        if (presenter.checkEmptyFields(fullName, email, password)) {
            Toast.makeText(this, "UM DOS CAMPOS ESTÁ VAZIO!", Toast.LENGTH_SHORT).show()
            return
        }

        presenter.signUpWithEmailAndPassword(fullName, email, password)
        toast(this, "REGISTRO EM ANDAMENTO")
    }

    override fun navigateToSignIn() { // TODO: 25/04/2021 Alternate activity
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}