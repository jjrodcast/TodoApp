package pe.com.jjorgerc.todoapp.presenter.main

import android.content.Context
import pe.com.jjorgerc.todoapp.model.main.MainModel
import pe.com.jjorgerc.todoapp.model.main.MainModelImp
import pe.com.jjorgerc.todoapp.model.entities.TaskKt
import pe.com.jjorgerc.todoapp.view.main.MainView

class MainPresenterImp(private val mainView: MainView) : MainPresenter, MainCallback {

    private var model: MainModel = MainModelImp(this)

    override fun getTaskFromDb() {
        model.getTaskFromSQLLite()
    }

    override fun requestData(tasks: ArrayList<TaskKt>) {
        mainView.loadTasks(tasks)
    }

    override fun deleteTaskFromDb(taskId: Int): Boolean {
        return model.deleteTaskFromSQLLite(taskId)
    }

    override fun getContext(): Context {
        return mainView.getContextActivity()
    }
}