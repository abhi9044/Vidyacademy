package com.holamed.meddapp;

import android.graphics.Bitmap;

/**
 * Created by Pradeep on 23-05-2015.
 */

public class ImageDBElement {
    private String Id,Name;
    private Bitmap image;
    private String type;//"pharmacy","testslab"


    public ImageDBElement(Bitmap photo, String name, String currID,String CurrType) {
        image=photo;
        Name=name;
        Id=currID;
        type=CurrType;

    }

    public Bitmap getImage() {
        return image;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setId(String labId) {
        this.Id = labId;
    }

    public void setName(String labName) {
        this.Name = labName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

