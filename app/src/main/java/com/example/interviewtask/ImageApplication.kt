package com.example.interviewtask

import android.app.Application
import com.example.interviewtask.di.ApplicationComponent
import com.example.interviewtask.di.DaggerApplicationComponent

class ImageApplication : Application() {

    companion object {

        @Volatile
        lateinit var INSTANCE: ImageApplication
    }

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

//        INSTANCE = this

        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}