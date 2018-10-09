package pe.com.jjorgerc.todoapp.model.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import pe.com.jjorgerc.todoapp.model.entities.TaskKt
import pe.com.jjorgerc.todoapp.utils.SqlKey

class SqlLiteDataBaseKt(private val context: Context) : SQLiteOpenHelper(context, SqlKey.DB_NAME, null, SqlKey.DB_VERSION) {

    companion object {
        val TAG: String = SqlLiteDataBaseKt::class.java.simpleName
    }

    override fun onCreate(db: SQLiteDatabase) {
        //super.onCreate(db)
        db.execSQL(SqlKey.CREATE_DB_COMMAND)

        Log.d(TAG, "onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SqlKey.UPGRADE_DB_COMMAND)
        onCreate(db)
        Log.d(TAG, "onUpgrade")
    }

    fun getAllTasks(): ArrayList<TaskKt> {

        val tasks = arrayListOf<TaskKt>()
        val db = readableDatabase
        val cursor = db.rawQuery(SqlKey.SELECT_ALL_TASKS, null);

        while (cursor.moveToNext()) {
            val task = TaskKt(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3))

            tasks.add(task)
        }

        cursor.close()
        db.close()

        return tasks
    }

    fun insertTask(name: String, description: String, status: String): Boolean {

        val db = writableDatabase

        val sql = "INSERT INTO tasks (task_name, task_description, task_status) VALUES ('$name', '$description', '$status');"

        val response = try {
            db.execSQL(sql)
            true

        } catch (e: Exception) {
            false
        }

        db.close()

        return response
    }

    fun updateTask(id: Int, status: String): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(SqlKey.TASK_STATUS, status)

        return try {
            db.update(SqlKey.DB_TABLE, values, "${SqlKey.TASK_ID}=?", arrayOf(id.toString()))
            true
        } catch (e: Exception) {
            false
        }
    }

    fun deleteTask(id: Int): Boolean {
        val db = writableDatabase

        return try {
            db.delete(SqlKey.DB_TABLE, "${SqlKey.TASK_ID}=?", arrayOf(id.toString()))
            true
        } catch (e: Exception) {
            false
        }
    }

}