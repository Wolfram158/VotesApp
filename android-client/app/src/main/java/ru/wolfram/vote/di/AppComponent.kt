package ru.wolfram.vote.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        DispatchersModule::class,
        SecurityModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}