package com.example.todo.ui.home.editTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R
import com.example.todo.database.MyDataBase
import com.example.todo.database.model.Task
import com.example.todo.databinding.ActivityTaskDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.todo.ui.formatTime
import com.example.todo.ui.formatDate
import com.example.todo.ui.getDateOnly
import com.example.todo.ui.getTimeOnly
import com.example.todo.ui.home.tasks.TasksAdapter
import com.example.todo.ui.showDialog
import java.util.Calendar

class EditTaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskDetailsBinding
    private var selectedDate: Date? = null
    private var selectedTime: Date? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        showTaskData()

        binding.content.btnSave.setOnClickListener {
            editTask()
        }
    }

    private fun editTask() {
        if (!isValidTaskInput())
            return
        val taskId = intent.getIntExtra("taskId", -1)
        val updatedTask = Task(
            id = taskId,
            title = binding.content.title.text.toString(),
            content = binding.content.description.text.toString(),
            date = selectedDate?.time ?: 0L, // Include the updated date
            time = selectedTime?.time ?: 0L // Include the updated time
        )

        MyDataBase.getInstance()
            .getTasksDao()
            .updateTask(updatedTask)

        finish()

    }

    private fun isValidTaskInput(): Boolean {
        var isValid = true
        val title = binding.title.text.toString()
        val description = binding.content.description.text.toString()
        val date = binding.content.dateTv.text.toString()
        val time = binding.content.timeTv.text.toString()
        if (title.isBlank()) {
            binding.content.titleTil.error = "Please enter task title"
            isValid = false
        } else {
            binding.content.titleTil.error = null
        }

        if (description.isBlank()) {
            binding.content.descriptionTil.error = "Please enter task description"
            isValid = false
        } else {
            binding.content.descriptionTil.error = null
        }
        if (date.isBlank()) {
            binding.content.dateTil.error = "Please enter task date"
            isValid = false
        }
        if (time.isBlank()) {
            binding.content.timeTil.error = "Please enter task time"
            isValid = false
        }
        return isValid
    }

    private fun showTaskData() {
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val dateLong = intent.getLongExtra("date", 0L)
        val timeLong = intent.getLongExtra("time", 0L)
        selectedDate = Date(dateLong)
        selectedTime = Date(timeLong)

        binding.content.titleTil.editText?.setText(title)
        binding.content.descriptionTil.editText?.setText(content)

        updateDateTextView(selectedDate)
        updateTimeTextView(selectedTime)

        binding.content.dateTv.setOnClickListener {
            showDatePicker()
        }

        binding.content.timeTv.setOnClickListener {
            showTimePicker()
        }
    }

    private fun updateDateTextView(date: Date?) {
        date?.let {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            binding.content.dateTv.text = dateFormat.format(date)
        }
    }

    private fun updateTimeTextView(time: Date?) {
        time?.let {
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            binding.content.timeTv.text = timeFormat.format(time)
        }
    }

    private fun showTimePicker() {
        val timePicker = TimePickerDialog(
            this,
            R.style.Base_Theme_ToD_TimePicker,
            { dialog, hourOfDay, minute ->
                calendar.set(android.icu.util.Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(android.icu.util.Calendar.MINUTE, minute)
                binding.content.timeTv.text = calendar.formatTime()
                binding.content.timeTil.error = null
            },
            calendar.get(android.icu.util.Calendar.HOUR_OF_DAY),
            calendar.get(android.icu.util.Calendar.MINUTE),
            false
        )
        //timePicker.window?.setBackgroundDrawableResource(R.color.backgroundColor)
        timePicker.show()
    }

    val calendar = android.icu.util.Calendar.getInstance()
    private fun showDatePicker() {
        val datePicker = DatePickerDialog(this, R.style.Base_Theme_ToDo_DatePicker)
        //datePicker.window?.setBackgroundDrawableResource(R.color.backgroundColor)
        datePicker.setOnDateSetListener { dialog, year, month, day ->
            calendar.set(android.icu.util.Calendar.DAY_OF_MONTH, day)
            calendar.set(android.icu.util.Calendar.MONTH, month)
            calendar.set(android.icu.util.Calendar.YEAR, year)
            binding.content.dateTv.text = calendar.formatDate()
            binding.content.dateTil.error = null
        }
        datePicker.show()
    }
}