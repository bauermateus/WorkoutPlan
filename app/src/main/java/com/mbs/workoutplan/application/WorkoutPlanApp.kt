package com.mbs.workoutplan.application

import android.app.Application
import com.mbs.workoutplan.di.repositoryModule
import com.mbs.workoutplan.di.serviceModule
import com.mbs.workoutplan.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WorkoutPlanApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WorkoutPlanApp)
            modules(viewModelModule, repositoryModule, serviceModule)
        }
    }
}