package com.salim.jobScheduler.intrfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.salim.jobScheduler.models.Task

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("select * from tasks_table where isChecked = 1")
    fun getAllDoneTasks(): LiveData<List<Task>>

    @Query("select * from tasks_table where isChecked = 0")
    fun getAllPendingTasks(): LiveData<List<Task>>

    @Query("DELETE FROM tasks_table WHERE isChecked = 1")
    fun deleteAll()

    @Query("delete from tasks_table where id = :id")
    fun deleteTaskById(id: Int)


}