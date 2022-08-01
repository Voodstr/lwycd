package ru.voodster.lwycd

import java.sql.Date

data class CheckableTask(
    var text: String,
    var completion: Boolean
)

data class TaskFolder(
    var name: String,
    var completion: Boolean,
    val checkableTasks : List<CheckableTask>,
)