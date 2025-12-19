package ru.wolfram.vote

import android.app.Application
import ru.wolfram.vote.di.AppComponent
import ru.wolfram.vote.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent
    }
}