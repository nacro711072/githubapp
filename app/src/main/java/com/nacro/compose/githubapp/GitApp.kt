package com.nacro.compose.githubapp

import android.app.Application
import com.nacro.compose.githubapp.di.mapperModule
import com.nacro.compose.githubapp.di.repositoryModule
import com.nacro.compose.githubapp.di.serviceModule
import com.nacro.compose.githubapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GitApp)
            val moduleList = arrayListOf(
                serviceModule,
                mapperModule,
                repositoryModule,
                viewModelModule
            )

            modules(moduleList)
        }

    }
}