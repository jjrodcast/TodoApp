package pe.com.jjorgerc.todoapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pe.com.jjorgerc.todoapp.model.Task;

public class SqLiteDataBase extends SQLiteOpenHelper {

    private Context context;

    public SqLiteDataBase(Context context) {
        super(context, SqlKey.DB_NAME, null, SqlKey.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlKey.CREATE_DB_COMMAND);
        Log.i("SQLiteDb", "Tabla creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SqlKey.UPGRADE_DB_COMMAND);
        onCreate(db);
    }

    // 1 -> completed
    // 2 -> incompleted
    // 3 -> deleted
    public List<Task> getTasks() {

        SQLiteDatabase db = getReadableDatabase();
        List<Task> tasks = new ArrayList<>();

        Cursor cursor = db.rawQuery(SqlKey.SELECT_ALL_TASKS, null);

        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getInt(0));
            task.setName(cursor.getString(1));
            task.setDescription(cursor.getString(2));
            task.setStatus(cursor.getString(3));

            tasks.add(task);
        }

        cursor.close();
        db.close();

        return tasks;
    }

    public boolean insertTask(String name, String description, String status) {

        boolean response;
        SQLiteDatabase db = getWritableDatabase();

        String sql = "INSERT INTO tasks (task_name, task_description, task_status) VALUES ('" + name + "', '" + description + "', '" + status + "');";

        try {
            db.execSQL(sql);
            response = true;

        } catch (Exception e) {
            response = false;
        }
        db.close();
        return response;
    }

    public boolean deleteTask(int id) {

        int rows;
        SQLiteDatabase db = getWritableDatabase();

        try {
            rows = db.delete(SqlKey.DB_TABLE, SqlKey.TASK_ID + "=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            rows = 0;
        }
        db.close();
        return rows > 0;
    }

    public boolean updateStatus(int id, String status) {

        int rows;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SqlKey.TASK_STATUS, status);

        try {
            rows = db.update(SqlKey.DB_TABLE, values, SqlKey.TASK_ID + "=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            rows = 0;
        }
        db.close();
        return rows > 0;
    }
}
