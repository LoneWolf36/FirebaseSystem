package sih.firebasesendnotif;

import android.location.Location;

import java.sql.Time;
import java.util.Date;

/**
 * Created by groot on 21/3/18.
 */

public class ScheduleData {
    String date;
    String time;
    String duration;

    public ScheduleData(String date, String time, String duration) {
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

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
}
