package com.schedulingsimulator.schedulingsimulator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Molly on 11/23/2015.
 */
public class PeriodicTask implements Parcelable {
    private int computationTime;
    private int period;

    public PeriodicTask(int compTime, int period) {
        this.computationTime = compTime;
        this.period = period;
    }

    public PeriodicTask(Parcel in)
    {
        computationTime = in.readInt();
        period = in.readInt();
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

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(computationTime);
        out.writeInt(period);
    }

    public static final Parcelable.Creator<PeriodicTask> CREATOR = new Parcelable.Creator<PeriodicTask>(){
        public PeriodicTask createFromParcel(Parcel in)
        {
            return new PeriodicTask(in);
        }

        public PeriodicTask[] newArray(int size)
        {
            return new PeriodicTask[size];
        }
    };

}
