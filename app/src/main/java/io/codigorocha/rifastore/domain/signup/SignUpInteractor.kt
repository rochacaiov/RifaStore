package io.codigorocha.rifastore.domain.signup

interface SignUpInteractor {

    interface SignUpCallback {
        fun onSignUpSucces()
        fun onSignUpFailure(error: String)
    }

    fun signUp(fullName: String, email: String, password: String, listener: SignUpCallback)

}