package com.schedulingsimulator.schedulingsimulator;

/**
 * Created by Molly on 11/23/2015.
 */
public class PeriodicTask {
    private int computationTime;
    private int period;

    public PeriodicTask(int compTime, int period) {
        this.computationTime = compTime;
        this.period = period;
    }

    public int getComputationTime()
    {
        return computationTime;
    }

    public int getPeriod()
    {
        return period;
    }

    public void setPeriod(int newPeriod)
    {
        this.period = newPeriod;
    }

    public void setComputationTime(int newCompTime)
    {
        this.computationTime = newCompTime;
    }

    @Override
    public String toString()
    {
        return "(" + computationTime + ", " + period + ")";
    }

}
