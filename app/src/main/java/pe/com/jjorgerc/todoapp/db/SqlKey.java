package pe.com.jjorgerc.todoapp.db;


public class SqlKey {

    static final String DB_NAME = "todolist";
    static final String DB_TABLE = "tasks";
    static final int DB_VERSION = 1;

    static final String TASK_ID = "id";
    static final String TASK_NAME = "task_name";
    static final String TASK_DESCRIPTION = "task_description";
    static final String TASK_STATUS = "task_status";

    static final String CREATE_DB_COMMAND = "CREATE TABLE " + DB_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, task_name TEXT, task_description TEXT, task_status TEXT);";
    static final String UPGRADE_DB_COMMAND = "DROP TABLE IF EXISTS " + DB_TABLE;

    static final String SELECT_ALL_TASKS = "SELECT " + TASK_ID + ", " + TASK_NAME + ", " + TASK_DESCRIPTION + ", " + TASK_STATUS + " FROM " + DB_TABLE;

}
