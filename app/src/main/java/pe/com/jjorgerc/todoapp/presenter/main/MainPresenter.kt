package pe.com.jjorgerc.todoapp.presenter.main

interface MainPresenter {

    fun getTaskFromDb()

    fun deleteTaskFromDb(taskId: Int) : Boolean
}