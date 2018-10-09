package pe.com.jjorgerc.todoapp.presenter.task

import pe.com.jjorgerc.todoapp.model.entities.TaskKt

interface TaskPresenter {

    fun validateFields(name: String, description: String): Boolean

    fun updateTaskFromDb(task: TaskKt): Boolean

    fun insertTaskFromDb(task: TaskKt): Boolean

}