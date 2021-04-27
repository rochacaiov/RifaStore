package io.codigorocha.rifastore.domain.signin

interface SignInInteractor {

    suspend fun signInWithEmailAndPassword(email:String,password:String)

}