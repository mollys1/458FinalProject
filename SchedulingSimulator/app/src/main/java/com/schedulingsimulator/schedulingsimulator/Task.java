package com.schedulingsimulator.schedulingsimulator;

/**
 * Created by Amy on 12/16/2015.
 */
public class Task
{
    private int readyTime;
    private int computationTime;
    private int deadline;
    private int period;
    private int computedTime;
    private String id;

    public Task(String id)
    {
        this.readyTime = 0;
        this.computationTime = 0;
        this.deadline = 0;
        this.period = 0;
        this.computedTime = 0;
        this.id = id;
    }

    public Task()
    {
        this.readyTime = 0;
        this.computationTime = 0;
        this.deadline = 0;
        this.period = 0;
        this.computedTime = 0;
        this.id = "-1";
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
