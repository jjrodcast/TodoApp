package pe.com.jjorgerc.todoapp.view.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_task.view.*
import pe.com.jjorgerc.todoapp.R
import pe.com.jjorgerc.todoapp.model.entities.TaskKt

class TaskKtAdapter(private val context: Context, private val cickListener: OnClickListener) : RecyclerView.Adapter<TaskKtAdapter.ViewHolder>() {

    private var tasks = arrayListOf<TaskKt>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_task, parent, false)
        return ViewHolder(view, context, cickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun addData(taskList: ArrayList<TaskKt>) {
        tasks.clear()
        tasks.addAll(taskList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, private val context: Context, private val listener: OnClickListener) : RecyclerView.ViewHolder(itemView) {

        private var completedStatus = "completed"

        fun bind(task: TaskKt) = with(itemView) {

            task_name.text = task.name
            task_description.text = task.description
            task_status.text = task.status

            if (task.status == completedStatus) {
                task_status.setTextColor(ContextCompat.getColor(context, R.color.colorCompleted))
            } else {
                task_status.setTextColor(ContextCompat.getColor(context, R.color.colorDivider))
            }

            imgDeleteTask.setOnClickListener { listener.onDelete(task) }
            imgEditTask.setOnClickListener { listener.onEdit(task) }
        }
    }

    interface OnClickListener {
        fun onDelete(task: TaskKt)
        fun onEdit(task: TaskKt)
    }

}
