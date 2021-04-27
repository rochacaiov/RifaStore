package io.codigorocha.rifastore.ui.auth.signin.view

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import io.codigorocha.rifastore.BaseActivity
import io.codigorocha.rifastore.R
import io.codigorocha.rifastore.RifaStore
import io.codigorocha.rifastore.ui.auth.signin.SignInContract
import io.codigorocha.rifastore.ui.auth.signin.presenter.SignInPresenter
import io.codigorocha.rifastore.ui.auth.signup.view.SignUpActivity
import io.codigorocha.rifastore.ui.main.view.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import javax.inject.Inject

class SignInActivity : BaseActivity(), SignInContract.SignInView {

    @Inject
    lateinit var presenter: SignInPresenter

    private lateinit var googleSignInClient: GoogleSignInClient

    val RC_SIGN_IN = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as RifaStore).getAppComponent()?.inject(this)
        presenter.attachView(this)

        setSpannable(txt_register.text.toString(), Color.CYAN, 22, txt_register.length(), txt_register)
        setupGoogleSignIn()

        button_signIn_email.setOnClickListener {
            signInWithEmailAndPassword()
        }

        button_signIn_google.setOnClickListener {
            signInWithGoogle()
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_sign_in
    }

    override fun showError(error: String?) {
        error?.let { toast(this, it) }
    }

    override fun showProgressBar() {
        progressBar_signIn.visibility = View.VISIBLE
        button_signIn_email.visibility = View.GONE
    }

    override fun hideProgressBar() {
        progressBar_signIn.visibility = View.GONE
        button_signIn_email.visibility = View.VISIBLE
    }

    override fun setSpannable(text: String, color: Int, start: Int, end: Int, textView: TextView) {
        val spannable = SpannableString(text)
        val colorSpan = ForegroundColorSpan(color)
        val clickableSpan = object: ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
            }
        }

        spannable.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannable
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun setupGoogleSignIn() {
        googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(R.string.default_web_client_id.toString())
                .requestEmail()
                .build()
        )
    }

    override fun signInWithEmailAndPassword() {
        val email = input_email.text.toString().trim()
        val password = input_password_signIn.text.toString().trim()

        if (presenter.checkEmptyFields(email, password)) {
            toast(this, "UM DOS CAMPOS ESTÁ VAZIO!")
            return
        }

        presenter.signInUserWithEmailAndPassword(email, password)
    }

    override fun signInWithGoogle() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.dettachView()
        presenter.dettachJob()
    }

}