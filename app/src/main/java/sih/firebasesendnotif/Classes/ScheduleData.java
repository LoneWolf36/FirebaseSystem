package sih.firebasesendnotif.Classes;

/**
 * Created by groot on 21/3/18.
 */

public class ScheduleData {
    String date;
    String time;
    String duration;
    long temp;
    String status;
    String uid;
    String city_name;
    String dam_name;

    public ScheduleData(String date, String time, String duration, long temp, String status, String uid, String dam_name, String address, String lat, String lng) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.temp = temp;
        this.status = status;
        this.uid = uid;
        //this.city_name = city_name;
        this.dam_name = dam_name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    String address;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDam_name() {
        return dam_name;
    }

    public void setDam_name(String dam_name) {
        this.dam_name = dam_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    String lat;
    String lng;
    public ScheduleData(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getTemp() {
        return temp;
    }

    public void setTemp(long temp) {
        this.temp = temp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

//    public ScheduleData(String date, String time, String duration, int temp, String status, String uid) {
//
//        this.date = date;
//        this.time = time;
//        this.duration = duration;
//        this.temp = temp;
//        this.status = status;
//        this.uid = uid;
//    }
}
