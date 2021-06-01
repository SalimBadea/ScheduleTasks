package com.salim.myapp.ui.activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.salim.myapp.R
import com.salim.myapp.database.TasksDataBase
import com.salim.myapp.factory.TasksModelFactory
import com.salim.myapp.models.Task
import com.salim.myapp.repository.TasksRepository
import com.salim.myapp.ui.viewModels.TasksViewModel
import com.salim.myapp.utils.NotificationUtils
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_add_task.btn_cancel
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.android.synthetic.main.date_dialog.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EditTaskActivity : AppCompatActivity(), View.OnClickListener {

    private var c = Calendar.getInstance()
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var hour = 0
    private var minute = 0
    private var second = 0
    private var time: Long = 0
    private var date: Long = 0
    private var longdate: Long = 0
    var timeinfo: String? = null
    private var str: String? = null
    private var s: String? = null
    private var timepick: String? = null
    private var datepick: String? = null
    private var timestamppick: String? = null
    var dateinfo: StringBuilder? = null
    private lateinit var mDateButton: Button
    private lateinit var mTimeButton: Button
    private lateinit var mViewModel: TasksViewModel
    private lateinit var mRepository: TasksRepository
    private var mNotified = false
    var id: Int = 0
    var title: String? = null
    var content: String? = null
    var createdAt: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        id = intent.getIntExtra("id", 0)
        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        createdAt = intent.getStringExtra("date")

        mRepository = TasksRepository(TasksDataBase.getDatabase(this).tasksDao())
        mViewModel =
            ViewModelProvider(this, TasksModelFactory(application, mRepository))
                .get(TasksViewModel::class.java)

        edit_task_title.setText(title)
        edit_task_content.setText(content)
        edit_task_date.setText(createdAt)

        btn_edit.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        edit_task_date.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.edit_task_date -> {

                val dialog = Dialog(this@EditTaskActivity)
                val v = LayoutInflater.from(this).inflate(R.layout.date_dialog, null, false)
                dialog.setContentView(v)
                dialog.setCanceledOnTouchOutside(true)
                dialog.show()

                mDateButton = v.btn_date
                mTimeButton = v.btn_time

                mDateButton.setOnClickListener {
                    mYear = c.get(Calendar.YEAR)
                    mMonth = c.get(Calendar.MONTH)
                    mDay = c.get(Calendar.DAY_OF_MONTH)
                    date = c.timeInMillis / 1000L
                    val dialog = DatePickerDialog(
                        this@EditTaskActivity, mDateSetListener(), mYear, mMonth, mDay
                    )
                    dialog.datePicker.minDate = System.currentTimeMillis() - 1000
                    dialog.show()
                }
                mTimeButton.setOnClickListener {
                    hour = c.get(Calendar.HOUR_OF_DAY)
                    minute = c.get(Calendar.MINUTE)
                    second = c.get(Calendar.SECOND)
                    val mTimePicker: TimePickerDialog
                    mTimePicker =
                        TimePickerDialog(
                            this@EditTaskActivity,
                            TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                                hour = selectedHour
                                minute = selectedMinute
                                timeinfo =
                                    "$selectedHour:$selectedMinute:00"
                                mTimeButton.text = timeinfo
                                val c: Calendar = Calendar.getInstance()
                                c.set(Calendar.HOUR, hour)
                                c.set(Calendar.MINUTE, minute)
                                c.set(Calendar.SECOND, second)
                                time = c.timeInMillis / 1000L
                            }, hour, minute, true
                        )
                    // Yes 24 hour time
                    mTimePicker.setTitle("Select  Time")
                    mTimePicker.show()
                }
                timestamp(mYear, mMonth, mDay, hour, minute)

                v.btn_done.setOnClickListener {
                    c = Calendar.getInstance()
                    c[Calendar.MONTH] = "0$mMonth".toInt()
                    c[Calendar.YEAR] = mYear
                    c[Calendar.DAY_OF_MONTH] = mDay

                    c[Calendar.HOUR_OF_DAY] = hour
                    c[Calendar.MINUTE] = minute
                    c[Calendar.SECOND] = second
                    val result1 = c.timeInMillis / 1000L
                    timepick = mTimeButton.text.toString()
                    datepick = mDateButton.text.toString()
                    timestamppick = result1.toString()

                    s = "$datepick $timepick"

                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    try {
                        val date: Date = sdf.parse(s)
                        longdate = date.time

                        str = sdf.format(date)
                        if (str != null) {
                            edit_task_date.text = str
                            dialog.dismiss()
                        } else {
                            Toast.makeText(
                                this@EditTaskActivity,
                                "Please Select Date and Time",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }
            }

            R.id.btn_cancel -> {
                Toast.makeText(this, "Task not Edited" , Toast.LENGTH_LONG).show()
                finish()
            }

            R.id.btn_edit -> {
                editTask()
            }
        }
    }

    private fun editTask() {
        var title = edit_task_title.text.toString()
        var content = edit_task_content.text.toString()
        var createdAt = edit_task_date.text.toString()

        val task = Task(id, title, content, createdAt, longdate, false)

        if (!mNotified)
            NotificationUtils().setNotification(longdate, this@EditTaskActivity)

        mViewModel.insert(task)
        Toast.makeText(this, "Task is Edited", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun timestamp(mYear: Int, mMonth: Int, mDay: Int, hour: Int, minute: Int): Int {
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = mYear
        c[Calendar.MONTH] = mMonth
        c[Calendar.DAY_OF_MONTH] = mDay
        c[Calendar.HOUR] = hour
        c[Calendar.MINUTE] = minute
        c[Calendar.SECOND] = 0
        c[Calendar.MILLISECOND] = 0
        return (c.timeInMillis / 1000L).toInt()
    }

    inner class mDateSetListener : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            mYear = year
            mMonth = monthOfYear
            mDay = dayOfMonth
            val c = Calendar.getInstance()
            c[Calendar.YEAR] = mYear
            c[Calendar.MONTH] = mMonth
            c[Calendar.DAY_OF_MONTH] = mDay
            dateinfo =
                StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-")
                    .append(mDay)
            mDateButton.text = dateinfo
        }
    }
}