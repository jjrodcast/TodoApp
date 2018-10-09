package pe.com.jjorgerc.todoapp.presenter.task

import android.content.Context
import pe.com.jjorgerc.todoapp.model.entities.TaskKt
import pe.com.jjorgerc.todoapp.model.task.TaskModel
import pe.com.jjorgerc.todoapp.model.task.TaskModelImp
import pe.com.jjorgerc.todoapp.view.task.TaskView

class TaskPresenterImp(private val taskView: TaskView) : TaskPresenter, TaskCallback {

    private val taskModel: TaskModel = TaskModelImp(this)

    override fun validateFields(name: String, description: String): Boolean {
        return taskModel.validateFields(name, description)
    }

    override fun updateTaskFromDb(task: TaskKt): Boolean {
        return taskModel.updateTask(task)
    }

    override fun insertTaskFromDb(task: TaskKt): Boolean {
        return taskModel.insertTask(task)
    }

    override fun getContext(): Context {
        return taskView.getContextActivity()
    }
}