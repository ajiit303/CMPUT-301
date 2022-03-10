package com.example.rollcount;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;


public class GameSessions implements Parcelable {
    private String session;
    private int roll;
    private int side;
//    ArrayList<Integer> values = new ArrayList<Integers>();

    public GameSessions(String session, int roll, int side) {
        this.session = session;
        this.roll = roll;
        this.side = side;
    }

    protected GameSessions(Parcel in) {
        session = in.readString();
        roll = in.readInt();
        side = in.readInt();
    }

    public static final Creator<GameSessions> CREATOR = new Creator<GameSessions>() {
        @Override
        public GameSessions createFromParcel(Parcel in) {
            return new GameSessions(in);
        }

        @Override
        public GameSessions[] newArray(int size) {
            return new GameSessions[size];
        }
    };

    public String getSession() {
        return this.session;
    }

    public int getRoll() {
        return this.roll;
    }

    public int getSide() {
        return this.side;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(session);
        parcel.writeInt(roll);
        parcel.writeInt(side);
    }
}
