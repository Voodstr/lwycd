package ru.voodster.lwycd

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import java.sql.Date

@Parcelize
data class CheckableTask(
    @ColumnInfo var text: String,
    @ColumnInfo var completion: Boolean
):Parcelable

@Entity(tableName = "Checklist")
@Parcelize
data class TaskFolder(
    var name: String,
    var completion: Boolean,
    val checkableTasks : List<CheckableTask>,
    val dateOfCompletion:Date
):Parcelable