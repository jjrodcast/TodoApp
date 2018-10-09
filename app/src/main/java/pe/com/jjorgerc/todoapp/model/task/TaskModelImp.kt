package pe.com.jjorgerc.todoapp.model.task

import android.text.TextUtils
import pe.com.jjorgerc.todoapp.model.db.SqlLiteDataBaseKt
import pe.com.jjorgerc.todoapp.model.entities.TaskKt
import pe.com.jjorgerc.todoapp.presenter.task.TaskCallback

class TaskModelImp(private val taskCallback: TaskCallback) : TaskModel {

    private val db by lazy { createSqlLiteDB() }

    override fun validateFields(taskName: String, taskDescription: String): Boolean {
        return !TextUtils.isEmpty(taskName) && !TextUtils.isEmpty(taskDescription)
    }

    override fun updateTask(task: TaskKt): Boolean {
        return db.updateTask(task.id, task.status)
    }

    override fun insertTask(task: TaskKt): Boolean {
        return db.insertTask(task.name, task.description, task.status)
    }

    private fun createSqlLiteDB(): SqlLiteDataBaseKt {
        return SqlLiteDataBaseKt(taskCallback.getContext())
    }

}