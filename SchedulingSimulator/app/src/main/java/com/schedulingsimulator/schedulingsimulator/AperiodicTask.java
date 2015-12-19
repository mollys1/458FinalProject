package com.schedulingsimulator.schedulingsimulator;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Molly on 11/23/2015.
 */
public class AperiodicTask extends Task implements Parcelable {

    int originalReadyTime;

    public AperiodicTask(String id, int readyTime, int compTime, int deadline)
    {
        super(id, Color.RED); //all aperiodic tasks are colored red
        this.readyTime = readyTime;
        this.originalReadyTime = readyTime;
        this.computationTime = compTime;
        this.deadline = deadline;
    }

    public AperiodicTask(Parcel in)
    {
        readyTime = in.readInt();
        originalReadyTime = in.readInt();
        computationTime = in.readInt();
        deadline = in.readInt();
        id = in.readString();
        color = in.readInt();
    }

    public void resetReadyTime()
    {
        readyTime = originalReadyTime;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(readyTime);
        out.writeInt(originalReadyTime);
        out.writeInt(computationTime);
        out.writeInt(deadline);
        out.writeString(id);
        out.writeInt(color);
    }

    @Override
    public AperiodicTask clone()
    {
        AperiodicTask clone = new AperiodicTask(this.id, this.readyTime, this.computationTime, this.deadline);
        clone.period = this.period;
        clone.computedTime = this.computedTime;
        clone.color = this.color;
        return clone;
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

