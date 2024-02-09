package com.example.todo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.database.model.Task

@Dao
interface TasksDao {
    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
    //OR fun deleteTask(id: Int)

    @Query("select * from task")
    fun getAllTasks(): List<Task>

    @Query("select * from task where dateTime = :dateTime")
    fun getTasksByDate(dateTime: Long): List<Task>
}