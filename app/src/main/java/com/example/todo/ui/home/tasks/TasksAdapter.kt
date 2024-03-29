package com.example.todo.ui.home.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.MyDataBase
import com.example.todo.database.model.Task
import com.example.todo.databinding.ItemTaskBinding
import com.zerobranch.layout.SwipeLayout

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
        if (onImageClickListener != null) {
            holder.itemView.setOnClickListener {
                onImageClickListener?.onItemClick(position, task)
            }
        }
        holder.binding.swipeLayout.setOnActionsListener(object : SwipeLayout.SwipeActionsListener {
            override fun onOpen(direction: Int, isContinuous: Boolean) {
                if (direction == SwipeLayout.RIGHT) {
                    holder.binding.swipeLayout.close()
                    onDeleteClickListener?.onItemClick(position, task)
                } else if (direction == SwipeLayout.LEFT) {
                    holder.binding.swipeLayout.close()
                    onEditClickListener?.onItemClick(position, task)
                }
            }

            override fun onClose() {
            }
        })
    }

    var onEditClickListener: OnItemClickListener? = null
    var onDeleteClickListener: OnItemClickListener? = null
    var onImageClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int, task: Task)
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
            binding.btnTaskIsDone.setOnClickListener {
                task.isDone = !task.isDone
                if (task.isDone) {
                    binding.btnTaskIsDone.setBackgroundResource(R.drawable.is_done)
                } else {
                    binding.btnTaskIsDone.setBackgroundResource(R.drawable.not_done)
                }
                MyDataBase.getInstance().getTasksDao().updateTask(task)
            }
        }

    }
}