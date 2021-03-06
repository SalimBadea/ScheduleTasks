package com.salim.jobScheduler.repository

import androidx.lifecycle.LiveData
import com.salim.jobScheduler.intrfaces.TasksDao
import com.salim.jobScheduler.models.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

public class TasksRepository(private val tasksDao: TasksDao) {

    fun insert(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksDao.insertTask(task)
        }
    }

    fun delete() {
        CoroutineScope(Dispatchers.IO).launch {
            tasksDao.deleteAll()
        }
    }

    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksDao.deleteTaskById(id)
        }
    }

    fun update(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            tasksDao.updateTask(task)
        }
    }

    fun getAllDoneTasks(): LiveData<List<Task>> {
        return tasksDao.getAllDoneTasks()
    }

    fun getAllPendingTasks(): LiveData<List<Task>> {
        return tasksDao.getAllPendingTasks()
    }

}