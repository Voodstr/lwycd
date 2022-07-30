package ru.voodster.lwycd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChecklistViewModel:ViewModel() {

    data class Task(
        var text: String,
        var completion: Boolean
    )

    private val cachedList = mutableListOf(
        Task("get up", false),
        Task("brush teeth", false),
        Task("boil eggs", false),
        Task("make coffee", false),
        Task("eat breakfast", false),
        Task("dress up", false),
        Task("go to work", false),
    )
    private val currentList : List<Task>
        get() = cachedList.toList()


    private val _checklist = MutableStateFlow(currentList)
    val checklist = _checklist.asStateFlow()

    private fun writeChangesToList() {
        _checklist.update { currentList }
    }

    fun addTask(task: Task) {
        cachedList.add(task)
        writeChangesToList()
    }

    companion object {
        const val TAG = "ChecklistViewModel"
    }

}