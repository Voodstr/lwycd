package ru.voodster.lwycd.entities

import ru.voodster.lwycd.CheckListFolderState
import ru.voodster.lwycd.CheckableTask
import ru.voodster.lwycd.TaskFolder
import java.sql.Date
import java.util.*

val initialTasks = mutableListOf(
    CheckableTask("get up", false,1),
    CheckableTask("brush teeth", false,1),
    CheckableTask("boil eggs", false,1),
    CheckableTask("make coffee", false,1),
    CheckableTask("eat breakfast", false,1),
    CheckableTask("dress up", false,1),
    CheckableTask("go to work", false,1),
)

val currentDate = Date(Calendar.getInstance().timeInMillis)

val initialFolder = TaskFolder(0,"folderName",false, currentDate)

val initialChecklistFolderState = CheckListFolderState(initialFolder, initialTasks.reversed())