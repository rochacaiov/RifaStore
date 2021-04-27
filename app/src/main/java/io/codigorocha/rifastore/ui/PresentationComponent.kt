package io.codigorocha.rifastore.ui

import dagger.BindsInstance
import dagger.Component
import io.codigorocha.rifastore.ui.auth.signin.view.SignInActivity
import io.codigorocha.rifastore.ui.auth.signup.view.SignUpActivity
import javax.inject.Singleton


@Component(
    modules = [
        PresentationModule::class
    ]
)
@Singleton
interface PresentationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun presentationModule(presentationModule: PresentationModule): Builder
        fun build(): PresentationComponent
    }

    fun inject(signUpActivity: SignUpActivity)
    fun inject(signInActivity: SignInActivity)
}