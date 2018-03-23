package sih.firebasesendnotif;

import android.location.Location;

import java.sql.Time;
import java.util.Date;

/**
 * Created by groot on 21/3/18.
 */

public class ScheduleData {
    String date;
    String location;
    String duration;

    public ScheduleData(String date, String location) {
        this.date = date;
        this.location = location;
    }

    public ScheduleData(String date, String location, String duration) {
        this.date = date;
        this.location = location;
        this.duration = duration;
    }

    public String getDuration() {

        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
