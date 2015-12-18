package com.schedulingsimulator.schedulingsimulator;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Amy on 12/16/2015.
 */
public class Task
{

    protected int readyTime;
    protected int computationTime;
    protected int deadline;
    protected int period;
    protected int computedTime;
    protected String id;
    protected  int color;

    public Task(String id, int color)
    {
        this.readyTime = 0;
        this.computationTime = 0;
        this.deadline = 0;
        this.period = 0;
        this.computedTime = 0;
        this.id = id;
        this.color = color;
    }

    public Task(String id)
    {
        this.readyTime = 0;
        this.computationTime = 0;
        this.deadline = 0;
        this.period = 0;
        this.computedTime = 0;
        this.id = id;
        //generate random color
        Random ran = new Random();
        this.color = Color.rgb(256 - ran.nextInt(128), 256 - ran.nextInt(128), 256 - ran.nextInt(128));
    }

    public Task()
    {
        this.readyTime = 0;
        this.computationTime = 0;
        this.deadline = 0;
        this.period = 0;
        this.computedTime = 0;
        this.id = "-1";
        this.color = Color.rgb(0, 0, 0);
    }


    public int getReadyTime() { return readyTime; }

    public int getComputationTime() { return computationTime; }

    public int getDeadline()
    {
        return deadline;
    }

    public int getPeriod()
    {
        return period;
    }

    public int getComputedTime()
    {
        return computedTime;
    }

    public String getId()
    {
        return id;
    }

    public int getColor() { return color; }

    public void setReadyTime(int newReadyTime)
    {
        readyTime = newReadyTime;
    }

    public void setComputationTime(int newComputationTime)
    {
        computationTime = newComputationTime;
    }

    public void setDeadline(int newDeadline)
    {
        deadline = newDeadline;
    }

    public void setPeriod(int newPeriod) { period = newPeriod; }

    public void setComputedTime(int newComputedTime) { computedTime = newComputedTime; }

    public void setID(String newID) { id = newID; }
}
