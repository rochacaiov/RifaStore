package io.codigorocha.rifastore.ui

import dagger.Module
import dagger.Provides
import io.codigorocha.rifastore.domain.signin.SignInInteractor
import io.codigorocha.rifastore.domain.signin.SignInInteractorImpl
import io.codigorocha.rifastore.domain.signup.SignUpInteractor
import io.codigorocha.rifastore.domain.signup.SignUpInteractorImpl
import javax.inject.Singleton

@Module
class PresentationModule {

    @Provides
    @Singleton
    internal fun provideSignUpInteractor(): SignUpInteractor = SignUpInteractorImpl()

    @Provides
    @Singleton
    internal fun provideSignInInteractor(): SignInInteractor = SignInInteractorImpl()

}