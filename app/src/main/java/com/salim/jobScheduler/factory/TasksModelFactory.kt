package com.salim.jobScheduler.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salim.jobScheduler.repository.TasksRepository
import com.salim.jobScheduler.ui.viewModels.TasksViewModel


class TasksModelFactory(private val context: Application, private val repository: TasksRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TasksViewModel(context, repository) as T
    }
}