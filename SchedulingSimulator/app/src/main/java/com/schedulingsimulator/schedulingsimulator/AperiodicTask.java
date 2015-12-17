package com.schedulingsimulator.schedulingsimulator;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Molly on 11/23/2015.
 */
public class AperiodicTask extends Task implements Parcelable {

    private int readyTime;
    private int computationTime;
    private int deadline;
    private int period;

    public AperiodicTask(int readyTime, int compTime, int deadline)
    {
        super();
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

