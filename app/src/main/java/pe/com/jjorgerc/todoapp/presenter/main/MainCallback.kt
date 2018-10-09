package pe.com.jjorgerc.todoapp.presenter.main

import pe.com.jjorgerc.todoapp.model.entities.TaskKt
import pe.com.jjorgerc.todoapp.presenter.BaseCallback

interface MainCallback : BaseCallback {

    fun requestData(tasks: ArrayList<TaskKt>)
}
