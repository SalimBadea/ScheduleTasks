package com.salim.myapp.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.salim.myapp.models.Task
import com.salim.myapp.repository.TasksRepository

class TasksViewModel(private val app: Application, private val repository: TasksRepository) : AndroidViewModel(app) {

    fun insert(task: Task) {
        return repository.insert(task)
    }

    // Method #4
    fun update(task: Task) {
      return repository.update(task)
    }

    // Method #5
    fun getAllDoneTasks(): LiveData<List<Task>> {
        return repository.getAllDoneTasks()
    }

     fun getAllPendingTasks(): LiveData<List<Task>> {
        return repository.getAllPendingTasks()
    }

     // Method #2
     fun delete() {
         repository.delete()
     }

     // Method #3
     fun deleteById(id:Int){
         repository.deleteById(id)
     }

 }