package sih.firebasesendnotif.Classes;

/**
 * Created by groot on 25/3/18.
 */

public class EmergencyData {
    String text;
    String dam_name;
    String time;
    String city_name;

    public EmergencyData(String text, String dam_name, String time, String city_name) {
        this.text = text;
        this.dam_name = dam_name;
        this.time = time;
        this.city_name = city_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDam_name() {
        return dam_name;
    }

    public void setDam_name(String dam_name) {
        this.dam_name = dam_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
