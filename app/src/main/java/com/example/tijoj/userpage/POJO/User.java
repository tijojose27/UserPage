package com.example.tijoj.userpage.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by tijoj on 5/8/2018.
 */

public class User implements Parcelable{
    private String email;
    private String password;
    private String name;
    private int gender;
    private Date dob;
    private int genderInt;
    private int ageInt;
    private int race;
    private int religion;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }




    protected User(Parcel in) {
        email = in.readString();
        password = in.readString();
        name = in.readString();
        gender = in.readInt();
        genderInt = in.readInt();
        ageInt = in.readInt();
        race = in.readInt();
        religion = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeInt(gender);
        parcel.writeInt(genderInt);
        parcel.writeInt(ageInt);
        parcel.writeInt(race);
        parcel.writeInt(religion);
    }
}
