package com.droid.bookshelf

import android.app.Application

class BookShelfApplication: Application() {

   companion object {
       lateinit var application: BookShelfApplication
           private set
   }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}