package com.schedulingsimulator.schedulingsimulator;

import java.util.ArrayList;

/**
 * Created by Amy on 12/16/2015.
 */
public interface Scheduler
{
    public ArrayList<Task> GetSchedule();
    public boolean SchedulabilityTest();
}
