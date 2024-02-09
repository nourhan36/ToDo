package com.example.todo.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.database.dao.TasksDao
import com.example.todo.database.model.Task

@Database(entities = [Task::class] , version = 1, exportSchema = true)
abstract class MyDataBase: RoomDatabase() {
    abstract fun getTasksDao(): TasksDao

    companion object { //static
        private const val DATABASE_NAME = "tasks_database"

        private var database: MyDataBase? = null

        fun init(app:Application){
            database = Room.databaseBuilder(
                app.applicationContext,
                MyDataBase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

        fun getInstance(): MyDataBase {
            return database!!
        }
    }
}