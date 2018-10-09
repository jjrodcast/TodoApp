package pe.com.jjorgerc.todoapp.view.main

import pe.com.jjorgerc.todoapp.model.entities.TaskKt
import pe.com.jjorgerc.todoapp.view.base.BaseView

interface MainView : BaseView {

    fun loadTasks(task: ArrayList<TaskKt>)
}