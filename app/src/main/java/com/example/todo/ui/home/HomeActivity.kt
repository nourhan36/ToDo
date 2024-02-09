package com.example.todo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.database.MyDataBase
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.home.settings.SettingsFragment
import com.example.todo.ui.home.tasks.TasksFragment

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        MyDataBase.getInstance()
            .getTasksDao()
            .getAllTasks()
        initViews()
    }

    private fun initViews() {
        viewBinding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.tasks_menu_item) {
                pushFragment(TasksFragment())
                viewBinding.title.text = getString(R.string.to_do_list)
            } else if (menuItem.itemId == R.id.settings_menu_item) {
                pushFragment(SettingsFragment())
                viewBinding.title.text = getString(R.string.settings)
            }
            true
        }
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}