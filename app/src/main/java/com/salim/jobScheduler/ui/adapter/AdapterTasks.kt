package com.salim.jobScheduler.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.salim.jobScheduler.R
import com.salim.jobScheduler.models.Task
import com.salim.jobScheduler.ui.activities.EditTaskActivity
import kotlinx.android.synthetic.main.task_item_view.view.*


class AdapterTasks(private var mList: List<Task>, private var callBack: itemDeleted, private val context: Context) :
    RecyclerView.Adapter<AdapterTasks.TasksHolder>() {

    private val FADE_DURATION = 1000L
    private var lastPosition = -1

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
        setAnimation(holder.itemView, position)
//        setScaleAnimation(holder.itemView)
    }

    inner class TasksHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(task: Task) {


            itemView.task_title.text = task.title
            itemView.task_content.text = task.content
            itemView.task_date.text = task.createdAt

            itemView.iv_task_delete.setOnClickListener {
                callBack.clickdelete(task.id)
            }

            itemView.iv_task_edit.setOnClickListener {
                val intent = Intent(itemView.context, EditTaskActivity::class.java)
                intent.putExtra("id", task.id)
                intent.putExtra("title", task.title)
                intent.putExtra("content", task.content)
                intent.putExtra("date", task.createdAt)
                itemView.context.startActivity(intent)
            }

            itemView.ch_done.setOnClickListener {
                task.isChecked = !task.isChecked
                callBack.clickforupdate(task)
            }

            if (task.isChecked) {
                itemView.ch_done.isChecked = true
                itemView.ch_done.setTextColor(itemView.context.resources.getColor(R.color.blueLight))

                itemView.task_title.paintFlags =
                    itemView.task_title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                itemView.task_content.paintFlags =
                    itemView.task_content.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                itemView.task_date.paintFlags =
                    itemView.task_date.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                itemView.ch_done.isChecked = false

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

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION
        view.startAnimation(anim)
    }

    private fun setScaleAnimation(view: View) {
        val anim = ScaleAnimation(
            -100f,
            0.0f,
            100.0f,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        anim.duration = FADE_DURATION
        view.startAnimation(anim)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    interface itemDeleted {
        fun clickdelete(id: Int)
        fun clickforupdate(task: Task)
    }
}