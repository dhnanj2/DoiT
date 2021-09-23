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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TaskCursorAdapter extends CursorAdapter {

    public TaskCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        //Create and return new blank list item
        return LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Create reference of target elements
        TextView tv_todo = view.findViewById(R.id.task_to_do);
        TextView tv_dueby =  view.findViewById(R.id.due_by);

        //get data from database via cursor
        String val_todo = cursor.getString(1);
        String val_dueby = cursor.getString(2);

        //Populate list item view with data
        tv_dueby.setText(val_dueby);
        tv_todo.setText(val_todo);
    }
}
