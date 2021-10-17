package com.salim.jobScheduler.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salim.jobScheduler.R
import com.salim.jobScheduler.database.TasksDataBase
import com.salim.jobScheduler.factory.TasksModelFactory
import com.salim.jobScheduler.models.Task
import com.salim.jobScheduler.repository.TasksRepository
import com.salim.jobScheduler.ui.adapter.AdapterTasks
import com.salim.jobScheduler.ui.viewModels.TasksViewModel
import kotlinx.android.synthetic.main.fragment_done_tasks.*
import maes.tech.intentanim.CustomIntent

class DoneTasksFragment : Fragment(), AdapterTasks.itemDeleted {

    private lateinit var mViewModel: TasksViewModel
    private lateinit var mAdapterTasks: AdapterTasks
    private var mList: ArrayList<Task> = arrayListOf()
    private lateinit var mRepository : TasksRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done_tasks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        CustomIntent.customType(activity, "left-to-right")
        mRepository = TasksRepository(TasksDataBase.getDatabase(activity!!).tasksDao())
        mViewModel = ViewModelProvider(this, TasksModelFactory(activity!!.application, mRepository)).get(
            TasksViewModel::class.java)

        mAdapterTasks = AdapterTasks(mList, this, activity!!)
        mViewModel.getAllDoneTasks().observe(this , Observer { list ->
            if(list != null) {
                mList.clear()
                mList.addAll(list)
                mAdapterTasks.notifyDataSetChanged()
            }
            if (list.size > 0)
                delete_all_done.visibility = View.VISIBLE
            else
                delete_all_done.visibility = View.GONE
        })

        rec_done_tasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterTasks
        }

        delete_all_done.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle(getString(R.string.alert))
            builder.setMessage(getString(R.string.are_you_sure_delete_all_tasks))
            builder.setPositiveButton(getString(R.string.yes) , DialogInterface.OnClickListener { dialog, which ->
                mViewModel.delete()
                dialog.dismiss()
            })
            builder.setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            builder.create()
            builder.show()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = DoneTasksFragment()
    }

    override fun clickdelete(id: Int) {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Alert!")
        builder.setMessage("Are you sure to delete this Task ?")
        builder.setPositiveButton("yes" , DialogInterface.OnClickListener { dialog, which ->
            mViewModel.deleteById(id)
            dialog.dismiss()
        })
        builder.setNegativeButton("no", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        builder.create()
        builder.show()
    }

    override fun clickforupdate(task: Task) {
            mViewModel.update(task)
    }
}