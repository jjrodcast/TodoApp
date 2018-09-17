package pe.com.jjorgerc.todoapp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.com.jjorgerc.todoapp.R;
import pe.com.jjorgerc.todoapp.model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private OnClickListener cickListener;
    private Context context;
    private List<Task> tasks;

    public TaskAdapter(Context context, OnClickListener cickListener) {
        this.tasks = new ArrayList<>();
        this.context = context;
        this.cickListener = cickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addData(List<Task> tasks) {
        this.tasks.clear();
        this.tasks = tasks;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTaskName;
        private TextView txtTaskDescription;
        private TextView txtTaskStatus;
        private ImageView imgEditTask;
        private ImageView imgDeleteTask;


        ViewHolder(View itemView) {
            super(itemView);
        }

        void bind(final Task task) {

            txtTaskName = itemView.findViewById(R.id.task_name);
            txtTaskDescription = itemView.findViewById(R.id.task_description);
            txtTaskStatus = itemView.findViewById(R.id.task_status);
            imgDeleteTask = itemView.findViewById(R.id.imgDeleteTask);
            imgEditTask = itemView.findViewById(R.id.imgEditTask);

            txtTaskName.setText(task.getName());
            txtTaskDescription.setText(task.getDescription());

            if (task.getStatus().equals("completed")) {
                txtTaskStatus.setTextColor(ContextCompat.getColor(context,R.color.colorCompleted));
            } else {
                txtTaskStatus.setTextColor(ContextCompat.getColor(context,R.color.colorDivider));
            }

            txtTaskStatus.setText(task.getStatus());


            imgDeleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cickListener.onDelete(task);
                }
            });

            imgEditTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cickListener.onEdit(task);
                }
            });

        }
    }

    public interface OnClickListener {
        void onDelete(Task task);
        void onEdit(Task task);
    }

}
