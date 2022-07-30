package ru.voodster.lwycd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChecklistViewModel:ViewModel() {

    data class Task(
        var text: String,
        var completion: Boolean
    )

    private val tasksFromFolder = arrayListOf(
        Task("get up", false),
        Task("brush teeth", false),
        Task("boil eggs", false),
        Task("make coffee", false),
        Task("eat breakfast", false),
        Task("dress up", false),
        Task("go to work", false),
    )


    sealed class ChecklistState(open val value: List<Task>) {
        data class Ready(
            override var value: List<Task>
        ) : ChecklistState(value)

        data class Error(
            override val value: List<Task>,
            val exception: Exception
        ) : ChecklistState(value)
    }

    private val _checklistState = MutableStateFlow(ChecklistState.Ready(tasksFromFolder))
    val checklistState = _checklistState.asStateFlow()

    private fun update() {
        _checklistState.update { ChecklistState.Ready(tasksFromFolder) }
    }


    fun getData():ChecklistState{
        return ChecklistState.Ready(tasksFromFolder)
    }

    fun addTask(task: Task) {
        tasksFromFolder.add(task)
        _checklistState.update { ChecklistState.Ready(tasksFromFolder) }
    }

}