package io.codigorocha.rifastore

import android.app.Application
import io.codigorocha.rifastore.ui.DaggerPresentationComponent
import io.codigorocha.rifastore.ui.PresentationComponent
import io.codigorocha.rifastore.ui.PresentationModule

class RifaStore : Application() {

    private var appComponent: PresentationComponent? = null

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerPresentationComponent
            .builder()
            .presentationModule(PresentationModule())
            .build()
    }

    fun getAppComponent(): PresentationComponent? = appComponent

}