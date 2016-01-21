package com.holamed.meddapp;

/**
 * Created by Abhishek on 9/27/2015.
 */


public class EventsObject {
    private String name;
    String id;
    String price;
    boolean selected=false;
    public EventsObject(String id, String name,String price,boolean selected) {
        this.name = name;
        this.id=id;
        this.price=price;
        this.selected=selected;
    }

    public String getName() {
        return this.name;
    }

    public String getPrice() {
        return this.price;
    }

    public String getId() {
        return this.id;
    }


    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}