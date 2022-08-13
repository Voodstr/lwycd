package ru.voodster.lwycd

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.sql.Date


@Entity(tableName = "Tasks")
@Parcelize
data class CheckableTask(
    @ColumnInfo var text: String,
    @ColumnInfo var completion: Boolean,
    @ColumnInfo val folderID: Int,
    @PrimaryKey(autoGenerate = true)
    private val id: Int = 0
) : Parcelable


@Entity(tableName = "ChecklistFolders")
@Parcelize
data class TaskFolder(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo var name: String,
    @ColumnInfo var completion: Boolean,
    @ColumnInfo val dateOfCompletion: Date
) : Parcelable