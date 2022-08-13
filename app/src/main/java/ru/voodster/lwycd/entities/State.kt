package ru.voodster.lwycd

import androidx.compose.runtime.toMutableStateList

class CheckListFolderState(val folder: TaskFolder, initialTasks: List<CheckableTask>) {

    private val _tasks: MutableList<CheckableTask> = initialTasks.toMutableStateList()
    val tasks: List<CheckableTask> = _tasks

    fun addTask(msg: CheckableTask) {
        _tasks.add( msg) // Add to the beginning of the list
    }
}