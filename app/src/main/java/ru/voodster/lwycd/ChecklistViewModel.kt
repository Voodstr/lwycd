package ru.voodster.lwycd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.voodster.lwycd.entities.initialChecklistFolderState

class ChecklistViewModel : ViewModel() {


    private val cachedList = mutableListOf(
        CheckableTask("get up", false, 1),
        CheckableTask("brush teeth", false, 1),
        CheckableTask("boil eggs", false, 1),
        CheckableTask("make coffee", false, 1),
        CheckableTask("eat breakfast", false, 1),
        CheckableTask("dress up", false, 1),
        CheckableTask("go to work", false, 1),
    )
    private val currentList: List<CheckableTask>
        get() = cachedList.toList()

    private val exampleTaskState = initialChecklistFolderState

    private val _checklist = MutableStateFlow(currentList)
    val checklist = _checklist.asStateFlow()

    private fun writeChangesToList() {
        _checklist.update { currentList }
    }

    fun addTask(text: String, folder: Int) {
        cachedList.add(0, CheckableTask(text, false, folder))
        writeChangesToList()
    }

    //TODO not implemented yet
    fun onBackToFolders() {
    }

    companion object {
        const val TAG = "ChecklistViewModel"
    }

}