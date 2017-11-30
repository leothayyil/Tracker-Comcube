package com.example.user.tracker.POJO;

/**
 * Created by USER on 30-11-2017.
 */

public class ShopDetails {



    StringBuffer name;
    StringBuffer inTime;
    StringBuffer inLocation;
    StringBuffer outTime;
    StringBuffer outLocation;
    public ShopDetails(String name, String inTime, String inLocation, String outTime, String outLocation) {

    }

    public ShopDetails(StringBuffer id, StringBuffer shopName, StringBuffer inTime, StringBuffer inLocation, StringBuffer outTime, StringBuffer outLocation) {


        this.name = name;
        this.inTime = inTime;
        this.inLocation = inLocation;
        this.outTime = outTime;
        this.outLocation = outLocation;
    }

    public StringBuffer getName() {
        return name;
    }

    public void setName(StringBuffer name) {
        this.name = name;
    }

    public StringBuffer getInTime() {
        return inTime;
    }

    public void setInTime(StringBuffer inTime) {
        this.inTime = inTime;
    }

    public StringBuffer getInLocation() {
        return inLocation;
    }

    public void setInLocation(StringBuffer inLocation) {
        this.inLocation = inLocation;
    }

    public StringBuffer getOutTime() {
        return outTime;
    }

    public void setOutTime(StringBuffer outTime) {
        this.outTime = outTime;
    }

    public StringBuffer getOutLocation() {
        return outLocation;
    }

    public void setOutLocation(StringBuffer outLocation) {
        this.outLocation = outLocation;
    }
}
