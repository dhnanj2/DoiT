package com.example.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.todo.data.TaskContract;
import com.example.todo.data.TaskDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {


    private static TextView mTV_date;

    private static TextView mTV_time;

    private EditText mET_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        mTV_date = (TextView) findViewById(R.id.date);
        mTV_time = (TextView) findViewById(R.id.time);
        mET_task = (EditText) findViewById(R.id.et_add_task);

        mTV_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });
    }

    public void insertTask(View v) {

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
        }catch (IllegalArgumentException e) {
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
            String time="";
            if(hourOfDay<=9) time += "0";
            time += hourOfDay;
            time+=":";
            if(minute<=9) time += "0";
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
            String date="";
            date+=year+"/";
            date+=month+"/";
            date+=day;
            mTV_date.setText(date);
        }
    }
}
