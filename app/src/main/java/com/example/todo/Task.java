package com.example.todo;

import java.sql.Time;
import java.util.Calendar;

//Class to specify a task and store its instances
public class Task {

    //decription of the task
    private String mDescription;

    //due date of the task
    private  Calendar mDueBy;

    /*
        *@param Description is the description of the task (What to do)
        *@param DueBy is the Due time (Date+time) of the task (When to do)
     */
    public Task(String Description,Calendar DueBy) {
        mDescription=Description;
        mDueBy=DueBy;
    }

    //return the Description of the task in string form
    public String getDescription() {
        return mDescription;
    }

    //return the Due date of the task in string form
    public String getDueDate() {
        return mDueBy.toString();
    }

}
