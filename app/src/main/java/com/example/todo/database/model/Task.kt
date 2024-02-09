package com.example.todo.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo
    var title: String? = null,
    @ColumnInfo
    var content: String? = null,
    @ColumnInfo
    var isDone: Boolean = false,
    @ColumnInfo
    var dateTime: Long? = null
)