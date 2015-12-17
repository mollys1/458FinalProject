package com.schedulingsimulator.schedulingsimulator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Amy on 12/16/2015.
 */
public class PollingServerScheduler implements Scheduler
{
    private ArrayList<Task> schedule;
    private ArrayList<PeriodicTask> periodicTasks;
    private ArrayList<AperiodicTask> aperiodicTasks;
    private int Cs;
    private int Ps;

    public PollingServerScheduler (int serverComputationTime, int serverPeriod, ArrayList<PeriodicTask> ptask, ArrayList<AperiodicTask> atask)
    {
        schedule = new ArrayList<Task>();
        periodicTasks = ptask;
        aperiodicTasks = atask;
        Cs = serverComputationTime;
        Ps = serverPeriod;
    }

    public ArrayList<Task> GetSchedule()
    {
        calculateSchedule();
        return schedule;
    }

    private void calculateSchedule()
    {
        int totalTime = findTimePeriod();
        int curTime = 0;
        Task curTask = null;
        for(curTime = 0; curTime < totalTime; curTime++)
        {
            curTask = findNextTask(curTime);
            schedule.add(curTime, curTask);
            if(curTask != null)
            {
                curTask.setComputedTime(curTask.getComputedTime() + 1);
                // Update the ready time
                if (curTask.getComputedTime() == curTask.getComputationTime()) {
                    curTask.setReadyTime(curTask.getReadyTime() + curTask.getPeriod());
                    curTask.setComputedTime(0);
                }
            }
        }
    }

    private int findTimePeriod()
    {
        // Find the least common multiple
        int LCM = 1;
        Iterator<PeriodicTask> perIter = periodicTasks.iterator();
        Task curTask;
        while(perIter.hasNext())
        {
            curTask = perIter.next();
            LCM *= curTask.getPeriod();
        }
        LCM *= Ps;

        // TODO Add in part for aperiodic tasks


        return LCM;
    }

    private Task findNextTask(int curTime)
    {
        // Find next task
        int minPeriod = Integer.MAX_VALUE;
        Task highestPriorityTask = null;
        Iterator<PeriodicTask> perIter = periodicTasks.iterator();
        Task curTask = null;
        while(perIter.hasNext())
        {
            curTask = perIter.next();
            // Only consider it if the ready time is less than the current time
            if(curTask.getReadyTime() <= curTime && curTask.getComputedTime() < curTask.getComputationTime() && curTask.getPeriod() < minPeriod)
            {
                highestPriorityTask = curTask;
            }
        }

        if(Ps < minPeriod)
        {
            // TODO Find an aperiodic task to run if available
        }

        return highestPriorityTask;
    }

    public boolean SchedulabilityTest()
    {
        // Add up the ci/pi for periodic tasks
        Iterator<PeriodicTask> perIter = periodicTasks.iterator();
        Task curtask;
        float sum = 0;
        int numTasks = 0;
        while(perIter.hasNext())
        {
            numTasks++;
            curtask = perIter.next();
            sum += (float) curtask.getComputationTime()/(float) curtask.getPeriod();
        }

        // Add in the periodic server
        sum += (float) Cs/(float)Ps;

        // See if the aperiodic tasks are schedulable
        boolean schedulable = sum < (numTasks + 1)*(2^(1/(numTasks+1)) - 1);
        if(!schedulable)
        {
            return false;
        }else{
            return true; // TODO add in to see if the aperiodic tasks are able to be scheduled
        }
    }
}
