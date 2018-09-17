package pe.com.jjorgerc.todoapp.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pe.com.jjorgerc.todoapp.R;
import pe.com.jjorgerc.todoapp.db.SqLiteDataBase;
import pe.com.jjorgerc.todoapp.db.SqlKey;
import pe.com.jjorgerc.todoapp.model.Task;
import pe.com.jjorgerc.todoapp.view.adapter.TaskAdapter;

public class MainActivity extends AppCompatActivity implements TaskView, TaskAdapter.OnClickListener {

    private RecyclerView taskRecyclerView;
    private FloatingActionButton fab;
    private TaskAdapter taskAdapter;
    private SqLiteDataBase db;
    private List<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        createAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    private void init() {
        createSqlLiteDatabase();

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        fab = findViewById(R.id.fabNewTask);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });

    }

    private void createSqlLiteDatabase() {
        db = new SqLiteDataBase(getContext());
    }

    private void createAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration decorator = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);

        decorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider)));

        taskAdapter = new TaskAdapter(getContext(),this);

        taskRecyclerView.setHasFixedSize(true);
        taskRecyclerView.addItemDecoration(decorator);
        taskRecyclerView.setLayoutManager(layoutManager);
        taskRecyclerView.setAdapter(taskAdapter);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    private void loadTasks() {
        tasks = db.getTasks();
        taskAdapter.addData(tasks);
    }

    @Override
    public void onDelete(Task task) {
        boolean response = db.deleteTask(task.getId());

        if (response) {
            Toast.makeText(getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
            loadTasks();
        } else {
            Toast.makeText(getContext(), "Can't delete task", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEdit(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskActivity.class);
        intent.putExtra("id", task.getId());
        intent.putExtra("name",task.getName());
        intent.putExtra("description", task.getDescription());
        intent.putExtra("status", task.getStatus());
        startActivity(intent);
    }
}
