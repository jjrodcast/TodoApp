package pe.com.jjorgerc.todoapp.model.task

import pe.com.jjorgerc.todoapp.model.entities.TaskKt

interface TaskModel {

    fun validateFields(taskName: String, taskDescription: String): Boolean

    fun updateTask(task: TaskKt): Boolean

    fun insertTask(task: TaskKt): Boolean
}