package com.salim.jobScheduler.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salim.jobScheduler.intrfaces.TasksDao
import com.salim.jobScheduler.models.Task

@Database(entities = [Task::class], version = 2)
abstract class TasksDataBase : RoomDatabase() {

     abstract fun tasksDao(): TasksDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TasksDataBase? = null

        fun getDatabase(context: Context): TasksDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, TasksDataBase::class.java, "tasks_table")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}