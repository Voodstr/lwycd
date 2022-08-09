package ru.voodster.lwycd.db

import androidx.room.*
import io.reactivex.rxjava3.core.Single
import ru.voodster.lwycd.CheckableTasks

@Dao
interface TasksDao {
    @Query("select checkableTaskIDs from Checklist")
    fun getTasks(): Single<List<Int>>
    //@Query("insert into Checklist ")
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTask(vararg tasks: CheckableTasks)


}