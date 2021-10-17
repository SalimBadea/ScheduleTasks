package com.salim.jobScheduler.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks_table")
class Task(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String?,
    var content: String?,
    var createdAt: String?,
    var date: Long? = null,
    var isChecked: Boolean = false
)