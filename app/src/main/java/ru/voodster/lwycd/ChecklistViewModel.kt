package ru.voodster.lwycd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChecklistViewModel:ViewModel() {



    private val cachedList = mutableListOf(
        CheckableTask("get up", false),
        CheckableTask("brush teeth", false),
        CheckableTask("boil eggs", false),
        CheckableTask("make coffee", false),
        CheckableTask("eat breakfast", false),
        CheckableTask("dress up", false),
        CheckableTask("go to work", false),
    )
    private val currentList : List<CheckableTask>
        get() = cachedList.toList()


    private val _checklist = MutableStateFlow(currentList)
    val checklist = _checklist.asStateFlow()

    private fun writeChangesToList() {
        _checklist.update { currentList }
    }

    fun addTask(task: CheckableTask) {
        cachedList.add(task)
        writeChangesToList()
    }

    companion object {
        const val TAG = "ChecklistViewModel"
    }

}