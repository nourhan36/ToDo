package com.example.todo

import android.app.Application
import com.example.todo.database.MyDataBase

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //initialize
        MyDataBase.init(this)
    }
}