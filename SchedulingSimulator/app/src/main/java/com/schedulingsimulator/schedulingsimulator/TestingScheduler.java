package com.schedulingsimulator.schedulingsimulator;

import java.util.ArrayList;

/**
 * Created by Molly on 12/17/2015.
 */
public class TestingScheduler implements Scheduler
{
    public ArrayList<Task> GetSchedule()
    {
        ArrayList<Task> testArray = new ArrayList<Task>();
        Task A = new Task("A");
        Task B = new Task("B");
        Task C = new Task("C");
        for (int i = 0; i < 5; i++) //add A for 5 time periods
        {
            testArray.add(A);
        }
        for (int i = 0; i < 2; i++ ) //add B for 2 time periods
        {
            testArray.add(B);
        }
        for (int i = 0; i < 4; i++) //add C for 4 time periods
        {
            testArray.add(C);
        }
        return testArray;
    }

    public boolean SchedulabilityTest()
    {
        return true;
    }

}
