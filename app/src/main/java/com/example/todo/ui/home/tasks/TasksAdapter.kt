package com.example.todo.ui.home.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.database.model.Task
import com.example.todo.databinding.ItemTaskBinding

class TasksAdapter(var tasks: MutableList<Task>? = null) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding: ItemTaskBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = tasks?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks!![position]
        holder.bind(task)
    }

    fun changeData(allTasks: List<Task>) {
        if (tasks == null)
            tasks = mutableListOf()
        tasks?.clear()
        tasks?.addAll(allTasks)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.title.text = task.title
            binding.description.text = task.content
        }
    }
}