package io.codigorocha.rifastore.domain.signin

import com.google.firebase.auth.FirebaseAuth
import io.codigorocha.rifastore.ui.auth.signin.exceptions.FirebaseSignInException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SignInInteractorImpl : SignInInteractor {

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Unit =
        suspendCancellableCoroutine { continuation ->
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(Unit)
                } else {
                    continuation.resumeWithException(FirebaseSignInException(it.exception?.message))
                }
            }
        }

}