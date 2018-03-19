package com.team.deltacentauri.preciselyvague;

/**
 * Created by LoneWolf on 3/19/18.
 */

public class notification {

    private String id;
    private String data;

    public notification(String id, String data){
        this.data = data;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }
}
