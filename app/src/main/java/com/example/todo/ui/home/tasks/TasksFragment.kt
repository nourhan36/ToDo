package com.example.todo.ui.home.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todo.database.MyDataBase
import com.example.todo.databinding.FragmentTasksBinding

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
        setUpRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        retrieveTasksList()
    }

    private fun retrieveTasksList() {
        val allTasks = MyDataBase.getInstance()
            .getTasksDao()
            .getAllTasks()

        adapter.changeData(allTasks)
    }

    val adapter = TasksAdapter()
    private fun setUpRecyclerView() {
        binding.rvTasks.adapter = adapter
    }
}