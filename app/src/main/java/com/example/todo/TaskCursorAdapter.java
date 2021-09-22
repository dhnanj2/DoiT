package com.example.todo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.todo.Task;

import java.util.ArrayList;

public class TaskCursorAdapter extends CursorAdapter {

    public TaskCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        //Create and return new blank list item
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Populate list item view with data
    }
}
