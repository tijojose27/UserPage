package com.example.tijoj.userpage.POJO;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by tijoj on 5/8/2018.
 */

public class User implements Parcelable{
    private String email;
    private String password;
    private String name;
    private int zip;
    private int gender;
    private String dob;
    private int genderPref;
    private int ageMin;
    private int ageMax;
    private String race;
    private String religion;
    private String height;
    private Uri profilePicUri;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected User(Parcel in) {
        email = in.readString();
        password = in.readString();
        name = in.readString();
        zip = in.readInt();
        gender = in.readInt();
        dob = in.readString();
        genderPref = in.readInt();
        ageMin = in.readInt();
        ageMax = in.readInt();
        race = in.readString();
        religion = in.readString();
        height = in.readString();
        profilePicUri = in.readParcelable(Uri.class.getClassLoader());
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

    public String getEmail() {
        return email;
    }

    public String getHeight() {
        String mHeight = height.substring(0,1)+"'"+height.substring(1,height.length())+"\"";
        return mHeight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return (String.valueOf(zip));
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getGender() {
        return getGenderFromInt(gender);
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAge(){
        String personYear = dob.substring(dob.length() -4);
        int mYear = Integer.valueOf(personYear);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.valueOf(year-mYear);
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGenderPref() {
        return getGenderFromInt(genderPref);
    }

    public void setGenderPref(int genderPref) {
        this.genderPref = genderPref;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Uri getProfilePicUri() {
        return profilePicUri;
    }

    public void setProfilePicUri(Uri profilePicUri) {
        this.profilePicUri = profilePicUri;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeInt(zip);
        parcel.writeInt(gender);
        parcel.writeString(dob);
        parcel.writeInt(genderPref);
        parcel.writeInt(ageMin);
        parcel.writeInt(ageMax);
        parcel.writeString(race);
        parcel.writeString(religion);
        parcel.writeString(height);
        parcel.writeParcelable(profilePicUri, i);
    }

    public String getGenderFromInt(int i){
        switch (i){
            case 1:
                return "Male";
            case 2:
                return "Female";
            case 3:
                return "Other";
            default:
                return "None";
        }
    }
}
