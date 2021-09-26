package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Calendar;

import com.example.todo.data.TaskContract;
import com.example.todo.data.TaskDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Calendar c = Calendar.getInstance();
    private TaskDBHelper mDbHelper;
    private static final int TaskLoader = 0;
    TaskCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG","FAB onClick Triggered");
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // Setup an Adapter to create a list item for each row of task data in the Cursor.
        // There is no task data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new TaskCursorAdapter(this, null);

        //find and populate the ListView
        ListView lst_view = (ListView) findViewById(R.id.list_view);
        lst_view.setAdapter(mCursorAdapter);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        lst_view.setEmptyView(emptyView);

        // Setup the item click listener
        lst_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("DEBUG","onCLick List View Triggered");
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Log.d("DEBUG","Intent Created");
                // Form the content URI that represents the specific task that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link TaskEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.tasks/tasks/2"
                // if the task with ID 2 was clicked on.
                Uri currentTaskUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentTaskUri);

                // Launch the {@link EditorActivity} to display the data for the current task.
                startActivity(intent);
                Log.d("DEBUG","Activity Launched");
            }
        });

        //kick-off the loader
        getLoaderManager().initLoader(TaskLoader,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //Define a projection to specify the columns to select from query table
        String [] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TASK_NAME,
                TaskContract.TaskEntry.COLUMN_DUEBY
        };
        //This Loader will execute the ContentProvider's query on Background Thread
        return new CursorLoader(this,
                TaskContract.TaskEntry.CONTENT_URI,
                projection,
                null,
                null,
                TaskContract.TaskEntry.COLUMN_DUEBY);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //Update {@Link TaskCursorAdapter with this new Adapter containing updated data
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Callback called when data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}


