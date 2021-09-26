package com.example.todo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.todo.data.TaskContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int EXISTING_PET_LOADER = 0;

    private static TextView mTV_date;

    private static TextView mTV_time;

    private EditText mET_task;

    private TextView mTV_remove;

    /**
     * Content URI for the existing task (null if it's a new task)
     */
    private Uri mCurrentTaskUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mTV_date = (TextView) findViewById(R.id.date);
        mTV_time = (TextView) findViewById(R.id.time);
        mET_task = (EditText) findViewById(R.id.et_add_task);
        FloatingActionButton mFab = findViewById(R.id.fab);
        mTV_remove = findViewById(R.id.tv_remove);

        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new task or editing an existing one.
        Intent intent = getIntent();
        mCurrentTaskUri = intent.getData();

        // If the intent DOES NOT contain a task content URI, then we know that we are
        // creating a new task.
        if (mCurrentTaskUri == null) {
            // This is a new task, so change the app bar to say "Add a Task"
            setTitle(getString(R.string.title_add_task));

            //set onClickListener of Fab to insertTask()
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertTask(view);
                }
            });

            //make remove option invisible
            mTV_remove.setVisibility(mTV_remove.getRootView().INVISIBLE);
        } else {
            // Otherwise this is an existing task, so change app bar to say "Edit Task"
            setTitle(getString(R.string.title_edit_task));

            // Initialize a loader to read the task data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);

            //set onClickListener on Fab to updateTask()
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateTask(view);
                }
            });

            //set onClickListener on TextView mTV_remove
            mTV_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeleteConfirmationDialog();
                }
            });
        }

        //set listener on mTV_time
        mTV_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });
        //set listener on mTV_date
        mTV_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

    }

    private void updateTask(View view) {
        //read inputs from the Views
        String entered_task = mET_task.getText().toString().trim();
        String entered_date = mTV_date.getText().toString().trim() + " " + mTV_time.getText().toString().trim();

        // Create a ContentValues object where column names are the keys,
        // and a row attributes are the values.
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TASK_NAME, entered_task);
        values.put(TaskContract.TaskEntry.COLUMN_DUEBY, entered_date);

        //update the task with content URI: mCurrentTaskUri
        // and pass in the new ContentValues. Pass in null for the selection and selection args
        // because mCurrentTaskUri will already identify the correct row in the database that
        // we want to modify.
        try {
            int rowsAffected = getContentResolver().update(mCurrentTaskUri, values, null, null);
            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, "Failed to Update Task!", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "Task Updated!", Toast.LENGTH_SHORT).show();
            }
            finish();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.toString().substring(35), Toast.LENGTH_SHORT).show();
        }
    }

    public void insertTask(View v) {
        //read inputs from the Views
        String entered_task = mET_task.getText().toString().trim();
        String entered_date = mTV_date.getText().toString().trim() + " " + mTV_time.getText().toString().trim();

        // Create a ContentValues object where column names are the keys,
        // and a row attributes are the values.
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TASK_NAME, entered_task);
        values.put(TaskContract.TaskEntry.COLUMN_DUEBY, entered_date);

        //Inset data into DB provider via ContentResolver
        try {
            getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, values);
            Toast.makeText(this, "Task Saved!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.toString().substring(35), Toast.LENGTH_SHORT).show();
        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all task attributes, define a projection that contains
        // all columns from the task table
        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TASK_NAME,
                TaskContract.TaskEntry.COLUMN_DUEBY};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentTaskUri,         // Query the content URI for the current task
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor d) {
        if (d.moveToFirst()) {
            String task = d.getString(1);
            String[] date = d.getString(2).split(" ");
            mTV_date.setText(date[0]);
            mTV_time.setText(date[1]);
            mET_task.setText(task);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Task Deletion");
        builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteTask();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the task in the database.
     */
    private void deleteTask() {
        // Call the ContentResolver to delete the task at the given content URI.
        // Pass in null for the selection and selection args because the mCurrentTaskUri
        // content URI already identifies the task that we want.
        int rowsDeleted = getContentResolver().delete(mCurrentTaskUri, null, null);

        // Show a toast message depending on whether or not the delete was successful.
        if (rowsDeleted == 0) {
            // If no rows were deleted, then there was an error with the delete.
            Toast.makeText(this,"Failed to delete Task!",Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the delete was successful and we can display a toast.
            Toast.makeText(this,"Task Deleted!",Toast.LENGTH_SHORT).show();
        }

        // Close the activity
        finish();
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String time = "";
            if (hourOfDay <= 9) time += "0";
            time += hourOfDay;
            time += ":";
            if (minute <= 9) time += "0";
            time += minute;
            mTV_time.setText(time);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String date = "";
            date += year + "/";
            date += month + 1 + "/";
            date += day;
            mTV_date.setText(date);
        }
    }
}
