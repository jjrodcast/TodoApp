package pe.com.jjorgerc.todoapp.model.main

import pe.com.jjorgerc.todoapp.model.db.SqlLiteDataBaseKt
import pe.com.jjorgerc.todoapp.presenter.main.MainCallback

class MainModelImp(private val callback: MainCallback) : MainModel {

    private val db by lazy { createSqlLiteDB() }

    override fun getTaskFromSQLLite() {
        val tasks = db.getAllTasks()
        callback.requestData(tasks)
    }

    private fun createSqlLiteDB(): SqlLiteDataBaseKt {
        return SqlLiteDataBaseKt(callback.getContext())
    }

    override fun deleteTaskFromSQLLite(taskId: Int): Boolean {
        return db.deleteTask(taskId)
    }
}