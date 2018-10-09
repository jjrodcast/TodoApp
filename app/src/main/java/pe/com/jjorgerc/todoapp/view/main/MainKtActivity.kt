package pe.com.jjorgerc.todoapp.view.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import pe.com.jjorgerc.todoapp.R
import pe.com.jjorgerc.todoapp.model.entities.TaskKt
import pe.com.jjorgerc.todoapp.presenter.main.MainPresenterImp
import pe.com.jjorgerc.todoapp.utils.Extras
import pe.com.jjorgerc.todoapp.utils.toast
import pe.com.jjorgerc.todoapp.view.adapter.TaskKtAdapter
import pe.com.jjorgerc.todoapp.view.task.TaskKtActivity

class MainKtActivity : AppCompatActivity(), MainView, TaskKtAdapter.OnClickListener {

    private val taskAdapter by lazy { createAdapter() }
    private val presenter by lazy { MainPresenterImp(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onResume() {
        super.onResume()
        presenter.getTaskFromDb()
    }

    override fun init() {
        createRecycler()

        fabNewTask.setOnClickListener {
            startActivity(Intent(this@MainKtActivity, TaskKtActivity::class.java))
        }
    }

    override fun onEdit(task: TaskKt) {
        val intent = Intent(this, TaskKtActivity::class.java)
        intent.putExtra(Extras.TASK_ID, task.id)
        intent.putExtra(Extras.TASK_NAME, task.name)
        intent.putExtra(Extras.TASK_DESCRIPTION, task.description)
        intent.putExtra(Extras.TASK_STATUS, task.status)
        startActivity(intent)
    }

    override fun onDelete(task: TaskKt) {
        val response = presenter.deleteTaskFromDb(task.id)
        showMessage(response, "Task deleted", "Can't delete task")
        if (response) presenter.getTaskFromDb()
    }

    override fun getContextActivity() = applicationContext!!

    private fun createAdapter(): TaskKtAdapter {
        return TaskKtAdapter(getContextActivity(), this)
    }

    private fun createRecycler() {
        val layoutManager = LinearLayoutManager(getContextActivity())
        val decorator = DividerItemDecoration(getContextActivity(), RecyclerView.VERTICAL)

        decorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider)!!)

        taskRecyclerView.setHasFixedSize(true)
        taskRecyclerView.addItemDecoration(decorator)
        taskRecyclerView.layoutManager = layoutManager
        taskRecyclerView.adapter = taskAdapter
    }

    override fun loadTasks(task: ArrayList<TaskKt>) {
        taskAdapter.addData(task)
    }

    private fun showMessage(response: Boolean, messageOk: String = "", messageWrong: String = "") {
        if (response) {
            toast(messageOk)
        } else {
            toast(messageWrong)
        }
    }

}
