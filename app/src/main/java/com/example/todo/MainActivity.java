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
import android.widget.ListView;
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

        //create a cursor to get data from provider
        Cursor cursor = getContentResolver().query(TaskContract.TaskEntry.CONTENT_URI,null,null,null, TaskContract.TaskEntry.COLUMN_DUEBY);
        //find ListView to populate
        ListView lst_view = (ListView) findViewById(R.id.list_view);
        //setup CursorAdapter
        TaskCursorAdapter adapter = new TaskCursorAdapter(this,cursor);
        //Attach CursorAdapter to ListView
        lst_view.setAdapter(adapter);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        lst_view.setEmptyView(emptyView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //create a cursor to get data from provider
        Cursor cursor = getContentResolver().query(TaskContract.TaskEntry.CONTENT_URI,null,null,null, TaskContract.TaskEntry.COLUMN_DUEBY);
        //find ListView to populate
        ListView lst_view = (ListView) findViewById(R.id.list_view);
        //setup CursorAdapter
        TaskCursorAdapter adapter = new TaskCursorAdapter(this,cursor);
        //Attach CursorAdapter to ListView
        lst_view.setAdapter(adapter);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        lst_view.setEmptyView(emptyView);
    }

}


