package sih.firebasesendnotif.Classes;

/**
 * Created by root on 28/3/18.
 */

public class LocationData {
    String city_name;
    String dam_name;
    String address;
    String lat;
    String lng;

    public LocationData()
    {}
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

    public LocationData(String city_name, String dam_name, String address, String lat, String lng) {
        this.city_name = city_name;
        this.dam_name = dam_name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }
}
