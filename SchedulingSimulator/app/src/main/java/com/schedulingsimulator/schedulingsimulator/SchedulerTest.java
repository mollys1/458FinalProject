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
        System.out.println("starting");
        ArrayList<PeriodicTask> ptasks = new ArrayList<>();
        ptasks.add(new PeriodicTask("P1", 1, 4));
        ptasks.add(new PeriodicTask("P2", 2, 6));

        ArrayList<AperiodicTask> atasks = new ArrayList<>();
        atasks.add(new AperiodicTask("A1", 2, 2, 20));
        atasks.add(new AperiodicTask("A2", 8, 1, 20));
        atasks.add(new AperiodicTask("A3", 13, 2, 20));

        Scheduler sched = new PollingServerScheduler(1, 5, ptasks, atasks);
        boolean possible = sched.SchedulabilityTest();

        if(possible)
        {
            ArrayList<Task> finalSchedule = sched.GetSchedule();
            System.out.println(finalSchedule.size());
            Iterator<Task> iter = finalSchedule.iterator();
            while(iter.hasNext())
            {
                System.out.println(iter.next());
            }
        }
        else{
            System.out.println("Impossible Schedule");
        }
    }
}
