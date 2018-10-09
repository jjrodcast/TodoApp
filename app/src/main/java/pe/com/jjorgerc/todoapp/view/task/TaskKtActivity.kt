package pe.com.jjorgerc.todoapp.view.task

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_task.*
import pe.com.jjorgerc.todoapp.R
import pe.com.jjorgerc.todoapp.model.entities.TaskKt
import pe.com.jjorgerc.todoapp.presenter.task.TaskPresenterImp
import pe.com.jjorgerc.todoapp.utils.Extras
import pe.com.jjorgerc.todoapp.utils.toast

class TaskKtActivity : AppCompatActivity(), TaskView {

    private val presenter by lazy { TaskPresenterImp(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        init()
    }

    override fun init() {
        getIntentExtras()

        btnSaveTask.setOnClickListener {
            val name = edtTaskName.text.toString()
            val description = edtTaskDescription.text.toString()
            if (presenter.validateFields(name, description)) {
                val status = when {
                    chkTaskStatus.isChecked -> "completed"
                    else -> "uncompleted"
                }

                val response = presenter.insertTaskFromDb(TaskKt(0, name, description, status))

                showMessage(response, "Task created", "Can't create task")

            } else {
                showMessage(false, messageWrong = "Complete empty fields")
            }
        }

        btnEditTask.setOnClickListener {
            val status = when {
                chkTaskStatus.isChecked -> "completed"
                else -> "uncompleted"
            }

            val response = presenter.updateTaskFromDb(TaskKt(intent.getIntExtra(Extras.TASK_ID, 0), "", "", status))

            showMessage(response, "Task updated", "Can't update task")
        }


    }

    override fun getContextActivity() = applicationContext!!

    private fun getIntentExtras() {
        intent.extras?.let {

            edtTaskName.setText(it.getString(Extras.TASK_NAME))
            edtTaskDescription.setText(it.getString(Extras.TASK_DESCRIPTION))
            val status: String = it.getString(Extras.TASK_STATUS)!!

            chkTaskStatus.isChecked = (status == Extras.TASK_STATUS_COMPLETED)

            edtTaskName.isEnabled = false
            edtTaskDescription.isEnabled = false
            btnEditTask.visibility = View.VISIBLE
            btnSaveTask.visibility = View.GONE
        }
    }

    private fun showMessage(response: Boolean, messageOk: String = "", messageWrong: String = "") {
        if (response) {
            toast(messageOk)
        } else {
            toast(messageWrong)
        }
    }

}
