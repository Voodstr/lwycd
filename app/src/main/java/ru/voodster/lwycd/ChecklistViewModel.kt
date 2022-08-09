package ru.voodster.lwycd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChecklistViewModel:ViewModel() {



    private val cachedList = mutableListOf(
        CheckableTasks("get up", false),
        CheckableTasks("brush teeth", false),
        CheckableTasks("boil eggs", false),
        CheckableTasks("make coffee", false),
        CheckableTasks("eat breakfast", false),
        CheckableTasks("dress up", false),
        CheckableTasks("go to work", false),
    )
    private val currentList : List<CheckableTasks>
        get() = cachedList.toList()


    private val _checklist = MutableStateFlow(currentList)
    val checklist = _checklist.asStateFlow()

    private fun writeChangesToList() {
        _checklist.update { currentList }
    }

    fun addTask(task: CheckableTasks) {
        cachedList.add(task)
        writeChangesToList()
    }

    fun onBackToFolders() {
        TODO("Not yet implemented")
    }

    companion object {
        const val TAG = "ChecklistViewModel"
    }

}