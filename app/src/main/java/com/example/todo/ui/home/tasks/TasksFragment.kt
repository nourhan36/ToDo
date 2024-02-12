package com.example.todo.ui.home.tasks

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.database.MyDataBase
import com.example.todo.database.model.Task
import com.example.todo.databinding.FragmentTasksBinding
import com.example.todo.ui.getDateOnly
import com.example.todo.ui.showDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.zerobranch.layout.SwipeLayout
import com.zerobranch.layout.SwipeLayout.SwipeActionsListener



class TasksFragment:Fragment() {
    lateinit var binding: FragmentTasksBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        isDone()
        swipeLayout()
    }

    private fun isDone(){
        adapter.onImageClickListener = object :TasksAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, task: Task) {
                task.isDone = !task.isDone
                adapter.notifyItemChanged(position)
            }
        }
    }

    private fun swipeLayout() {
        adapter.onDeleteClickListener = object :TasksAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, item: Task) {
                deleteTask(item)
            }

            private fun deleteTask(task: Task) {
                MyDataBase.getInstance()
                    .getTasksDao().deleteTask(task)
                showDialog(
                    "Task Deleted Successfully",
                    posActionName = "ok",
                    isCancelable = false
                )
                retrieveTasksList()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        retrieveTasksList()
    }

    val currentDate = Calendar.getInstance()
     fun retrieveTasksList() {
        val allTasks = MyDataBase.getInstance()
            .getTasksDao()
            .getTasksByDate(currentDate.getDateOnly())

        adapter.changeData(allTasks)
    }

    val adapter = TasksAdapter()
    private fun setUpViews() {
        binding.rvTasks.adapter = adapter
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                currentDate.set(date.year, date.month - 1, date.day)
                retrieveTasksList()
            }
        }
        binding.calendarView.selectedDate = CalendarDay.today()
    }
}