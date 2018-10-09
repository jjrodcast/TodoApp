package pe.com.jjorgerc.todoapp.model.main

interface MainModel {

    fun getTaskFromSQLLite()

    fun deleteTaskFromSQLLite(taskId: Int): Boolean
}