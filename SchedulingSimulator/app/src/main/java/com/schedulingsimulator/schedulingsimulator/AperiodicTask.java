package com.schedulingsimulator.schedulingsimulator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Molly on 11/23/2015.
 */
public class AperiodicTask implements Parcelable {

    private int readyTime;
    private int computationTime;
    private int deadline;

    public AperiodicTask(int readyTime, int compTime, int deadline)
    {
        this.readyTime = readyTime;
        this.computationTime = compTime;
        this.deadline = deadline;
    }

    public AperiodicTask(Parcel in)
    {
        readyTime = in.readInt();
        computationTime = in.readInt();
        deadline = in.readInt();
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(readyTime);
        out.writeInt(computationTime);
        out.writeInt(deadline);
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

    public static final Parcelable.Creator<AperiodicTask> CREATOR = new Parcelable.Creator<AperiodicTask>(){
        public AperiodicTask createFromParcel(Parcel in) {
            return new AperiodicTask(in);
        }

        public AperiodicTask[] newArray(int size) {
            return new AperiodicTask[size];
        }
    };
}

