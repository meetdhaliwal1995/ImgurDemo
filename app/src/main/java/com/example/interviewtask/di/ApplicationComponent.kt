package com.example.interviewtask.di

import com.example.interviewtask.ActivityUploadImage
import com.example.interviewtask.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
    fun injectUploadImage(mainActivity: ActivityUploadImage)
}