package io.codigorocha.rifastore.domain.signup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpInteractorImpl : SignUpInteractor {

    override fun signUp(fullName: String, email: String, password: String, listener: SignUpInteractor.SignUpCallback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { itSignUp ->
            if (itSignUp.isSuccessful) {
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()

                FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdate)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        listener.onSignUpSucces()
                    }
                }
            } else {
                listener.onSignUpFailure(itSignUp.exception?.message.toString())
            }

        }
    }

}