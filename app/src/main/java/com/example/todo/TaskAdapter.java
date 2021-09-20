package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.todo.Task;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Task} object located at this position in the list
        Task currentTask = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID task_to_do.
        TextView TaskToDo = (TextView) listItemView.findViewById(R.id.task_to_do);
        TaskToDo.setText(currentTask.getDescription());

        // Find the TextView in the list_item.xml layout with the ID due_by.
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.due_by);
        // Get the Due time from the currentTask object and set this time on
        // the TextView.
        defaultTextView.setText(currentTask.getDueDate());

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
