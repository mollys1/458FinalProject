package com.schedulingsimulator.schedulingsimulator;

/**
 * Created by Molly on 11/23/2015.
 */
public class AperiodicTask {

    private int readyTime;
    private int computationTime;
    private int deadline;

    public AperiodicTask(int readyTime, int compTime, int deadline)
    {
        this.readyTime = readyTime;
        this.computationTime = compTime;
        this.deadline = deadline;
    }

    public int getReadyTime()
    {
        return readyTime;
    }

    public int getComputationTime()
    {
        return computationTime;
    }

    public int getDeadline()
    {
        return deadline;
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

    @Override
    public String toString()
    {
        return "(" + readyTime + ", " + computationTime + ", " + deadline + ")";
    }
}

