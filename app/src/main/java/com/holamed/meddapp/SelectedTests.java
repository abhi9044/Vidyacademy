package com.holamed.meddapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Era on 5/5/2015.
 */
public class SelectedTests implements Parcelable {
    String selected_test_name;
    public SelectedTests()

    {

    }
    public SelectedTests(Parcel in)
   {
       selected_test_name=in.readString();
   }
    public String getSelected_test_name() {
        return selected_test_name;
    }

    public void setSelected_test_name(String selected_test_name) {
        this.selected_test_name = selected_test_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
             dest.writeString(selected_test_name);
    }
    public static final Parcelable.Creator<SelectedTests> CREATOR = new Parcelable.Creator<SelectedTests>() {
        public SelectedTests createFromParcel(Parcel in) {
            return new SelectedTests(in);
        }

        public SelectedTests[] newArray(int size) {
            return new SelectedTests[size];
        }
    };
}
