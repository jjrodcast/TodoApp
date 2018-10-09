package pe.com.jjorgerc.todoapp.utils

object SqlKey {
    
    const val DB_NAME = "todolist"
    const val DB_TABLE = "tasks"
    const val DB_VERSION = 1

    const val TASK_ID = "id"
    const val TASK_NAME = "task_name"
    const val TASK_DESCRIPTION = "task_description"
    const val TASK_STATUS = "task_status"

    const val CREATE_DB_COMMAND = "CREATE TABLE $DB_TABLE (id INTEGER PRIMARY KEY AUTOINCREMENT, task_name TEXT, task_description TEXT, task_status TEXT);"
    const val UPGRADE_DB_COMMAND = "DROP TABLE IF EXISTS $DB_TABLE"

    const val SELECT_ALL_TASKS = "SELECT $TASK_ID, $TASK_NAME, $TASK_DESCRIPTION, $TASK_STATUS FROM $DB_TABLE"
}