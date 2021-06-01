package com.salim.myapp.ui.adapter

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salim.myapp.R
import com.salim.myapp.models.Task
import com.salim.myapp.ui.activities.EditTaskActivity
import kotlinx.android.synthetic.main.task_item_view.view.*

class AdapterTasks(private var mList: List<Task>, private var callBack: itemDeleted) :
    RecyclerView.Adapter<AdapterTasks.TasksHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item_view, parent, false)
        return TasksHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: TasksHolder, position: Int) {
        holder.bindView(mList[position])
    }

    inner class TasksHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(task: Task) {
            itemView.task_title.text = task.title
            itemView.task_content.text = task.content
            itemView.task_date.text = task.createdAt

            itemView.task_card.setOnClickListener {
                val intent = Intent(itemView.context, EditTaskActivity::class.java)
                intent.putExtra("id", task.id)
                intent.putExtra("title", task.title)
                intent.putExtra("content", task.content)
                intent.putExtra("date", task.createdAt)
                itemView.context.startActivity(intent)
            }

            itemView.iv_task_delete.setOnClickListener {
                callBack.clickdelete(task.id)
            }

            itemView.ch_done.setOnClickListener {
                task.isChecked = !task.isChecked
                callBack.clickforupdate(task)
            }

            if (task.isChecked) {
                itemView.ch_done.isChecked = true

                itemView.task_title.setTextColor(itemView.context.resources.getColor(R.color.grey))
                itemView.task_content.setTextColor(itemView.context.resources.getColor(R.color.grey))

                itemView.task_title.paintFlags =
                    itemView.task_title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                itemView.task_content.paintFlags =
                    itemView.task_content.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                itemView.task_date.paintFlags =
                    itemView.task_date.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                itemView.ch_done.isChecked = false

                itemView.task_title.setTextColor(itemView.context.resources.getColor(R.color.black))
                itemView.task_content.setTextColor(itemView.context.resources.getColor(R.color.black))

                if ((itemView.task_title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) > 0)
                    itemView.task_title.paintFlags =
                        itemView.task_title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                if ((itemView.task_content.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) > 0)
                    itemView.task_content.paintFlags =
                        itemView.task_content.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                if ((itemView.task_date.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG) > 0)
                    itemView.task_date.paintFlags =
                        itemView.task_date.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    interface itemDeleted {
        fun clickdelete(id: Int)
        fun clickforupdate(task: Task)
    }
}