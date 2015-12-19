package com.schedulingsimulator.schedulingsimulator;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Created by Molly on 11/23/2015.
 */
public class PeriodicTask extends Task implements Parcelable {
    public boolean aperiodicServer;

    public PeriodicTask(String id, int compTime, int period, int red, int green, int blue) {
        super(id, Color.rgb(red, green, blue));
        this.computationTime = compTime;
        this.period = period;
    }

    public PeriodicTask(String id, int compTime, int period)
    {
        super(id);
        this.computationTime = compTime;
        this.period = period;
        aperiodicServer = false;
    }

    public PeriodicTask(Parcel in)
    {
        computationTime = in.readInt();
        period = in.readInt();
        id = in.readString();
        color = in.readInt();
    }

    @Override
    public PeriodicTask clone()
    {
        PeriodicTask clone = new PeriodicTask(this.id, this.computationTime, this.period);
        clone.readyTime = this.readyTime;
        clone.deadline = this.deadline;
        clone.color = this.color;
        return clone;
    }

    public boolean isServerTask()
    {
        return aperiodicServer;
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
        out.writeString(id);
        out.writeInt(color);
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
