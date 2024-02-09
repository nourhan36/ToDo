package com.example.todo.ui.home.addTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.example.todo.R
import com.example.todo.database.MyDataBase
import com.example.todo.database.model.Task
import com.example.todo.databinding.FragmentAddTaskBinding
import com.example.todo.ui.formatDate
import com.example.todo.ui.formatTime
import com.example.todo.ui.getDateOnly
import com.example.todo.ui.getTimeOnly
import com.example.todo.ui.showDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat

class AddTaskBottomSheet: BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()

    }

    private fun setUpViews() {
        binding.dateTil.setOnClickListener {
            showDatePicker()
        }
        binding.timeTil.setOnClickListener {
            showTimePicker()
        }
        binding.addTaskBtn.setOnClickListener {
            addTask()
        }
    }

    private fun showTimePicker() {
        val timePicker = TimePickerDialog(
            requireContext(),
            R.style.Base_Theme_ToD_TimePicker,
            { dialog, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                binding.timeTv.text = calendar.formatTime()
                binding.timeTil.error = null
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        //timePicker.window?.setBackgroundDrawableResource(R.color.backgroundColor)
        timePicker.show()
    }

    val calendar = Calendar.getInstance()
    private fun showDatePicker() {
        val datePicker = DatePickerDialog(requireContext(), R.style.Base_Theme_ToDo_DatePicker)
        //datePicker.window?.setBackgroundDrawableResource(R.color.backgroundColor)
        datePicker.setOnDateSetListener { dialog, year, month, day ->
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)
            binding.dateTv.text = calendar.formatDate()
            binding.dateTil.error = null
        }
        datePicker.show()
    }

    private fun isValidTaskInput(): Boolean {
        var isValid = true
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        val date = binding.dateTv.text.toString()
        val time = binding.timeTv.text.toString()
        if (title.isBlank()) {
            binding.titleTil.error = "Please enter task title"
            isValid = false
        } else {
            binding.titleTil.error = null
        }

        if (description.isBlank()) {
            binding.descriptionTil.error = "Please enter task description"
            isValid = false
        } else {
            binding.descriptionTil.error = null
        }
        if (date.isBlank()) {
            binding.dateTil.error = "Please enter task date"
            isValid = false
        }
        if (time.isBlank()) {
            binding.timeTil.error = "Please enter task time"
            isValid = false
        }
        return isValid
    }

    private fun addTask() {
        if (!isValidTaskInput())
            return

        MyDataBase.getInstance()
            .getTasksDao()
            .insertTask(
                Task(
                    title = binding.title.text.toString(),
                    content = binding.description.text.toString(),
                    date = calendar.getDateOnly(),
                    time = calendar.getTimeOnly()
                )
            )
        showDialog(
            "Task Inserted Successfully",
            posActionName = "ok",
            posActionCallBack = {
                dismiss()
                onTaskAddedListener?.onTaskAdded()
            },
            isCancelable = false
        )
    }

    var onTaskAddedListener: OnTaskAddedListener? = null

    fun interface OnTaskAddedListener {
        fun onTaskAdded()
    }
}