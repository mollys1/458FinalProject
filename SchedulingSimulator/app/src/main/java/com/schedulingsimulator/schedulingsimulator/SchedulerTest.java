package com.schedulingsimulator.schedulingsimulator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Amy on 12/17/2015.
 */
public class SchedulerTest
{
    public static void main(String[] args)
    {
        ArrayList<PeriodicTask> ptasks = new ArrayList<>();
        ptasks.add(new PeriodicTask("P1", 1, 3));
        ptasks.add(new PeriodicTask("P2", 2, 4));

        ArrayList<AperiodicTask> atasks = new ArrayList<>();
        Scheduler sched = new PollingServerScheduler(2, 5, ptasks, atasks);
        boolean possible = sched.SchedulabilityTest();
        if(possible)
        {
            ArrayList<Task> finalSchedule = sched.GetSchedule();
            Iterator<Task> iter = finalSchedule.iterator();
            while(iter.hasNext())
            {
                System.out.println(iter.next());
            }
        }
    }
}
