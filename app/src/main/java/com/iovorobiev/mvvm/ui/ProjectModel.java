package com.iovorobiev.mvvm.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectModel implements Parcelable {
    public int id;
    public final String name;
    public final boolean cheap;
    public final boolean fast;
    public final boolean stable;

    public ProjectModel(String name, boolean cheap, boolean fast, boolean stable) {
        this.name = name;
        this.cheap = cheap;
        this.fast = fast;
        this.stable = stable;
    }

    protected ProjectModel(Parcel in) {
        name = in.readString();
        cheap = in.readByte() != 0;
        fast = in.readByte() != 0;
        stable = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (cheap ? 1 : 0));
        dest.writeByte((byte) (fast ? 1 : 0));
        dest.writeByte((byte) (stable ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectModel> CREATOR = new Creator<ProjectModel>() {
        @Override
        public ProjectModel createFromParcel(Parcel in) {
            return new ProjectModel(in);
        }

        @Override
        public ProjectModel[] newArray(int size) {
            return new ProjectModel[size];
        }
    };
}
