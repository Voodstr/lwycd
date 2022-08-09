package ru.voodster.lwycd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChecklistViewModel:ViewModel() {



    private val cachedList = mutableListOf(
        CheckableTasks("get up", false,1),
        CheckableTasks("brush teeth", false,1),
        CheckableTasks("boil eggs", false,1),
        CheckableTasks("make coffee", false,1),
        CheckableTasks("eat breakfast", false,1),
        CheckableTasks("dress up", false,1),
        CheckableTasks("go to work", false,1),
    )
    private val currentList : List<CheckableTasks>
        get() = cachedList.toList()


    private val _checklist = MutableStateFlow(currentList)
    val checklist = _checklist.asStateFlow()

    private fun writeChangesToList() {
        _checklist.update { currentList }
    }

    fun addTask(text:String,folder: Int) {
        cachedList.add(CheckableTasks(text,false,folder))
        writeChangesToList()
    }

    //TODO not implemented yet
    fun onBackToFolders() {
    }

    companion object {
        const val TAG = "ChecklistViewModel"
    }

}