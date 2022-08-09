package ru.voodster.lwycd

import android.app.TaskInfo
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.sql.Date
import java.sql.RowId


@Entity(tableName = "Tasks")
@Parcelize
data class CheckableTasks(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo var text: String,
    @ColumnInfo var completion: Boolean,
    @ColumnInfo val folderID: Int,
):Parcelable


@Entity(tableName = "ChecklistFolders")
@Parcelize
data class TaskFolder(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo var name: String,
    @ColumnInfo var completion: Boolean,
    @ColumnInfo val dateOfCompletion:Date
):Parcelable