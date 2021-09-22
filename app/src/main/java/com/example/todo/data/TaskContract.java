package com.example.todo.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Tasks app.
 */
public final class TaskContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private TaskContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.todo";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.tasks/tasks/ is a valid path for
     * looking at task data. content://com.example.android.tasks/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_TASKS = "tasks";

    /**
     * Inner class that defines constant values for the tasks database table.
     * Each entry in the table represents a single task.
     */
    public static final class TaskEntry implements BaseColumns {

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TASKS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASKS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TASKS;

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
