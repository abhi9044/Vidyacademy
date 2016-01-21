package com.holamed.meddapp;

/**
 * Created by Abhishek on 9/27/2015.
 */


public class Alias {
    private String name;
    private String alias;
    String id;
    String type;
     boolean selected=false;
    public Alias(String name, String alias,String id,String type,boolean selected) {
        this.name = name;
        this.alias = alias;
        this.id=id;
        this.type=type;
this.selected=selected;
    }

    public String getName() {
        return this.name;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}