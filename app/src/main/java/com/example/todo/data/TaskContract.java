package com.example.todo.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Tasks app.
 */
public final class TaskContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private TaskContract() {}

    /**
     * Inner class that defines constant values for the tasks database table.
     * Each entry in the table represents a single pet.
     */
    public static final class TaskEntry implements BaseColumns {

        /** Name of database table for tasks */
        public final static String TABLE_NAME = "tasks";

        /**
         * Unique ID number for the task (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the task(describes what to do).
         *
         * Type: TEXT
         */
        public final static String COLUMN_TASK_NAME ="name";

        /**
         * Due Date and Time of the Task
         *
         * Type: TEXT
         */
        public final static String COLUMN_DUEBY ="due_by";
    }

}
