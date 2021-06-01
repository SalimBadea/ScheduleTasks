package com.salim.myapp.ui.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salim.myapp.R
import com.salim.myapp.database.TasksDataBase
import com.salim.myapp.factory.TasksModelFactory
import com.salim.myapp.models.Task
import com.salim.myapp.repository.TasksRepository
import com.salim.myapp.ui.adapter.AdapterTasks
import com.salim.myapp.ui.activities.AddTaskActivity
import com.salim.myapp.ui.viewModels.TasksViewModel
import kotlinx.android.synthetic.main.fragment_pending_tasks.*

class PendingTasksFragment : Fragment(), AdapterTasks.itemDeleted {

    private lateinit var mViewModel: TasksViewModel
    private lateinit var mAdapterTasks: AdapterTasks
    private var mList: ArrayList<Task> = arrayListOf()
    private lateinit var mRepository : TasksRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_tasks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRepository = TasksRepository(TasksDataBase.getDatabase(activity!!).tasksDao())
        mViewModel = ViewModelProvider(this, TasksModelFactory(activity!!.application, mRepository)).get(
            TasksViewModel::class.java)

        mAdapterTasks = AdapterTasks(mList, this)
        mViewModel.getAllPendingTasks().observe(this , Observer { list ->
            if(list != null) {
                mList.clear()
                mList.addAll(list)
                mAdapterTasks.notifyDataSetChanged()
            }
        })

        rec_tasks.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterTasks
        }

        btn_add.setOnClickListener {
            startActivity(Intent(context, AddTaskActivity::class.java))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PendingTasksFragment()
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