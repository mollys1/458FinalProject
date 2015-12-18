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
    private PeriodicTask AperServer;

    public PollingServerScheduler (int serverComputationTime, int serverPeriod, ArrayList<PeriodicTask> ptask, ArrayList<AperiodicTask> atask)
    {
        schedule = new ArrayList<Task>();
        periodicTasks = new ArrayList<>(ptask);
        aperiodicTasks = atask;
        AperServer = new PeriodicTask("S", serverComputationTime, serverPeriod);
        AperServer.aperiodicServer = true;
        periodicTasks.add(AperServer);
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
                if(curTask.getClass() == PeriodicTask.class) {
                    curTask.setComputedTime(curTask.getComputedTime() + 1);
                    // Update the ready time
                    if (curTask.getComputedTime() == curTask.getComputationTime()) {
                        curTask.setReadyTime(curTask.getReadyTime() + curTask.getPeriod());
                        curTask.setComputedTime(0);
                    }
                }else{
                    curTask.setComputedTime(curTask.getComputedTime() + 1);
                    AperServer.setComputedTime(AperServer.getComputedTime() + 1);
                    // Update the ready time
                    if (curTask.getComputedTime() == curTask.getComputationTime() || AperServer.getComputedTime() == AperServer.getComputationTime()) {
                        AperServer.setReadyTime(AperServer.getReadyTime() + AperServer.getPeriod());
                        AperServer.setComputedTime(0);
                    }
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
            LCM = lcm(curTask.getPeriod(), LCM);
        }

        // Aperiodic Task time
        Iterator<AperiodicTask> aperIter = aperiodicTasks.iterator();
        while(aperIter.hasNext())
        {
            curTask = aperIter.next();
            if(curTask.getReadyTime() + curTask.getComputationTime() > LCM)
            {
                LCM = curTask.getReadyTime() + curTask.getComputationTime();
            }
        }

        return LCM;
    }

    private Task findNextTask(int curTime)
    {
        // Find next task
        int minPeriod = Integer.MAX_VALUE;
        int minNonAperPeriod = Integer.MAX_VALUE;
        PeriodicTask highestPriorityTask = null;
        PeriodicTask highestNonAperPriorityTask= null;
        Iterator<PeriodicTask> perIter = periodicTasks.iterator();
        PeriodicTask curTask = null;
        while(perIter.hasNext())
        {
            curTask = perIter.next();
            // Only consider it if the ready time is less than the current time
            if(curTask.getReadyTime() <= curTime && curTask.getComputedTime() < curTask.getComputationTime() && curTask.getPeriod() < minPeriod)
            {
                minPeriod = curTask.getPeriod();
                highestPriorityTask = curTask;
            }
            // Only consider it if the ready time is less than the current time
            if(curTask.getReadyTime() <= curTime && curTask.getComputedTime() < curTask.getComputationTime() && curTask.getPeriod() < minNonAperPeriod && curTask.aperiodicServer == false)
            {
                minNonAperPeriod = curTask.getPeriod();
                highestNonAperPriorityTask = curTask;
            }
        }

        if(highestPriorityTask != null && highestPriorityTask.aperiodicServer)
        {
            // Find an aperiodic task to run if available
            ArrayList<AperiodicTask> readyTimeOrdered = orderAperiodicByReadyTime();
            Iterator<AperiodicTask> iter = readyTimeOrdered.iterator();
            AperiodicTask curATask = null;
            while(iter.hasNext())
            {
                curATask = iter.next();
                if(curATask.getComputedTime() != curATask.getComputationTime() && curATask.getReadyTime() < curTime)
                {
                    return curATask;
                }
            }

            // Aperiodic server gives up its time
            highestPriorityTask.setReadyTime(highestPriorityTask.getReadyTime() + highestPriorityTask.getPeriod());
            highestPriorityTask.setComputedTime(0);
            return highestNonAperPriorityTask;
        }

        return highestPriorityTask;
    }

    public boolean SchedulabilityTest()
    {
        boolean schedulable = schedulabilityTest();
        if(schedulable) return true;
        return exactAnalysis();
    }

    private boolean schedulabilityTest()
    {
        double summation = 0;
        for (int i = 0; i < periodicTasks.size(); i++)
        {
            PeriodicTask current = periodicTasks.get(i);
            summation += ((double) current.getComputationTime() / (double) current.getPeriod());
        }

        double rightSide = periodicTasks.size() * (Math.pow(2, 1.0/periodicTasks.size()) - 1);
        return summation <= rightSide;
    }

    private boolean exactAnalysis()
    {
        ArrayList<PeriodicTask> orderedByPeriod = orderPeriodicByPeriod();
        for (int i = 0; i < orderedByPeriod.size(); i++)
        {
            boolean taskSchedulable = completionTimeTest(i, orderedByPeriod);
            if (!taskSchedulable) return false;
        }
        return true;
    }

    //perform completion time test on given task, true if task is schedulable
    private boolean completionTimeTest(int index, ArrayList<PeriodicTask> sortedTasks)
    {
        PeriodicTask toCheck = sortedTasks.get(index);
        int completionTime = 0, previousCompletionTime = 1;
        while (previousCompletionTime <= toCheck.getPeriod())
        {
            for (int i = index; i < sortedTasks.size(); i++)
            {
                completionTime += Math.ceil((double) previousCompletionTime / sortedTasks.get(i).getPeriod()) * sortedTasks.get(i).getComputationTime();
            }
            if (previousCompletionTime == completionTime) return true;
            previousCompletionTime = completionTime;
            completionTime = 0;
        }
        return false;
    }

    private ArrayList<PeriodicTask> orderPeriodicByPeriod()
    {
        ArrayList<PeriodicTask> orderedByPeriod = new ArrayList<>(periodicTasks.size());
        orderedByPeriod.add(periodicTasks.get(0));
        for (int i = 1; i < periodicTasks.size(); i++)
        {
            PeriodicTask toInsert = periodicTasks.get(i);
            boolean inserted = false;
            for (int j = 0; j < orderedByPeriod.size() && !inserted; j++)
            {
                if (toInsert.getPeriod() >= orderedByPeriod.get(j).getPeriod())
                {
                    orderedByPeriod.add(j, toInsert);
                    inserted = true;
                }
                else if (j == orderedByPeriod.size() - 1)
                {
                    orderedByPeriod.add(toInsert);
                    inserted = true;
                }
            }
        }
        return orderedByPeriod;
    }

    private ArrayList<AperiodicTask> orderAperiodicByReadyTime()
    {
        ArrayList<AperiodicTask> orderedByReadyTime = new ArrayList<>(aperiodicTasks.size());
        orderedByReadyTime.add(aperiodicTasks.get(0));
        for (int i = 1; i < aperiodicTasks.size(); i++)
        {
            AperiodicTask toInsert = aperiodicTasks.get(i);
            boolean inserted = false;
            for (int j = 0; j < orderedByReadyTime.size() && !inserted; j++)
            {
                if (toInsert.getReadyTime() <= orderedByReadyTime.get(j).getReadyTime())
                {
                    orderedByReadyTime.add(j, toInsert);
                    inserted = true;
                }
                else if (j == orderedByReadyTime.size() - 1)
                {
                    orderedByReadyTime.add(toInsert);
                    inserted = true;
                }
            }
        }
        return orderedByReadyTime;
    }

    private static int gcd(int a, int b)
    {
        while (b > 0)
        {
            int temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    private static int lcm(int a, int b)
    {
        return a * (b / gcd(a, b));
    }
}
