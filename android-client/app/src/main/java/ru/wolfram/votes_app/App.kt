package ru.wolfram.votes_app

import android.app.Application
import ru.wolfram.common.data.network.service.ApiServiceTestImpl1
import ru.wolfram.common.di.AppComponent
import ru.wolfram.common.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext, ApiServiceTestImpl1())
    }

    override fun onCreate() {
        super.onCreate()
        appComponent
    }
}