package com.example.humraz.teckshetraada;

/**
 * Created by humra on 8/16/2016.
 */
public class ada {
    private String lat;
    private String longg;


    long stackId;
    public ada() {
      /*Blank default constructor essential for Firebase*/
    }
    public ada(String a)
    {

    }


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongg() {
        return longg;
    }

    public void setLongg(String longg) {
        this.longg = longg;
    }
}