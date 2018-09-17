package pe.com.jjorgerc.todoapp.view;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import pe.com.jjorgerc.todoapp.R;
import pe.com.jjorgerc.todoapp.db.SqLiteDataBase;

public class TaskActivity extends AppCompatActivity {

    private EditText edtTaskName, edtTaskDescription;
    private CheckBox chkTaskStatus;
    private Button btnSaveTask, btnEditTask;
    private int taskId;
    private SqLiteDataBase sqLiteDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        init();

        if (getIntent().getExtras() != null) {
            edtTaskName.setEnabled(false);
            edtTaskDescription.setEnabled(false);
            btnEditTask.setVisibility(View.VISIBLE);
            btnSaveTask.setVisibility(View.GONE);
            taskId = getIntent().getIntExtra("id", 0);
            edtTaskName.setText(getIntent().getStringExtra("name"));
            edtTaskDescription.setText(getIntent().getStringExtra("description"));
            String status = getIntent().getStringExtra("status");
            if (status.equals("completed")) {
                chkTaskStatus.setChecked(true);
            } else {
                chkTaskStatus.setChecked(false);
            }

        }
    }

    private void init() {
        sqLiteDataBase = new SqLiteDataBase(getApplicationContext());

        edtTaskName = findViewById(R.id.edtTaskName);
        edtTaskDescription = findViewById(R.id.edtTaskDescription);
        chkTaskStatus = findViewById(R.id.chkTaskStatus);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnEditTask = findViewById(R.id.btnEditTask);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {

                    boolean isChecked = chkTaskStatus.isChecked();
                    String status;
                    if (isChecked) {
                        status = "completed";
                    } else {
                        status = "uncompleted";
                    }

                    boolean response = sqLiteDataBase.insertTask(edtTaskName.getText().toString().trim(),
                            edtTaskDescription.getText().toString().trim(),
                            status);

                    if (response) {
                        Toast.makeText(getApplicationContext(), "Task Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Can't save the Task", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean response;
                if (chkTaskStatus.isChecked()) {
                    response = sqLiteDataBase.updateStatus(Objects.requireNonNull(getIntent().getExtras()).getInt("id", 0), "completed");
                } else {
                    response = sqLiteDataBase.updateStatus(taskId, "uncompleted");
                }

                if (response) {
                    Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Can't update the Task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean validate() {
        boolean response = false;
        if (edtTaskName.getText().toString().trim().length() == 0 ||
                edtTaskDescription.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Fill the empty fields", Toast.LENGTH_SHORT).show();
        } else {
            response = true;
        }
        return response;
    }
}
