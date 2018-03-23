package sih.firebasesendnotif;

import android.location.Location;

import java.util.Date;

/**
 * Created by groot on 21/3/18.
 */

public class ScheduleData {
    Date date;
    Location location;

    public ScheduleData(Date date, Location location) {
        this.date = date;
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
