package ru.voodster.lwycd.db

import androidx.room.*
import io.reactivex.rxjava3.core.Single
import ru.voodster.lwycd.CheckableTask

@Dao
interface TasksDao {
    @Query("select checkableTasks from Checklist")
    fun getTasks(): Single<List<CheckableTask>>
    //@Query("insert into Checklist ")
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(vararg tasks: CheckableTask)

}