package sih.firebasesendnotif.Classes;

/**
 * Created by groot on 21/3/18.
 */

public class ScheduleData {
    String date;
    String time;
    String duration;
    int temp;
    String status;
    String uid;
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

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
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

    public ScheduleData(String date, String time, String duration, int temp, String status, String uid) {

        this.date = date;
        this.time = time;
        this.duration = duration;
        this.temp = temp;
        this.status = status;
        this.uid = uid;
    }
}
