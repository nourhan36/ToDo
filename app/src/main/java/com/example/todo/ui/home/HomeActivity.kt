package com.example.todo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.database.MyDataBase
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.home.addTask.AddTaskBottomSheet
import com.example.todo.ui.home.settings.SettingsFragment
import com.example.todo.ui.home.tasks.TasksFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MyDataBase.getInstance()
            .getTasksDao()
            .getAllTasks()
        initViews()
    }

    private fun initViews() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.tasks_menu_item) {
                pushFragment(TasksFragment())
                binding.title.text = getString(R.string.to_do_list)
            } else if (menuItem.itemId == R.id.settings_menu_item) {
                pushFragment(SettingsFragment())
                binding.title.text = getString(R.string.settings)
            }
            true
        }
        binding.bottomNavigation.selectedItemId = R.id.tasks_menu_item
        binding.fabAddTask.setOnClickListener {
            showAddTaskBottomSheet()
        }
    }

    private fun showAddTaskBottomSheet() {
        val addTaskSheet = AddTaskBottomSheet()
        addTaskSheet.onTaskAddedListener = AddTaskBottomSheet.OnTaskAddedListener {
            //notify tasksFragment
            supportFragmentManager.fragments.forEach { fragment ->
                if (fragment is TasksFragment && fragment.isAdded) {
                    fragment.retrieveTasksList()
                }
            }
        }
        addTaskSheet.show(supportFragmentManager, null)
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}