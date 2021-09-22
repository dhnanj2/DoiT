package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.todo.Task;

import com.example.todo.data.TaskContract;
import com.example.todo.data.TaskDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private Calendar c = Calendar.getInstance();
    private TaskDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
            }
        });

        mDbHelper = new TaskDBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Create object to refer TextView on the MainActivity
        TextView displayView = (TextView) findViewById(R.id.main_text_view);
        displayView.setText("");

        //Define a Projection which specifies which columns to select/query/delete/update in the DB
        String[] projection={
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TASK_NAME,
                TaskContract.TaskEntry.COLUMN_DUEBY
        };

        //Perform a query on Provider using ContentResolver which returns the result in the form of cursor
        Cursor cursor = getContentResolver().query(TaskContract.TaskEntry.CONTENT_URI,null,null,null,null);

        boolean flg = cursor.moveToFirst();
        while (flg) {
            // Display the information at the current position of cursor.
            displayView.append("\t Task:  "+cursor.getString(1)+"\n\t Due by:  "+cursor.getString(2)+"\n\n");
            flg = cursor.moveToNext();
        }

        if(displayView.getText().toString().isEmpty()) {
            displayView.append("No Task To Do!!");
        }
        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.
        cursor.close();
    }

}


