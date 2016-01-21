package com.holamed.meddapp;

import java.util.ArrayList;

/**
 * Created by Shreyans on 12/16/2015.
 */
public class TestGroupObject {
    String testGroupName;
    ArrayList<TestResultObject> testResultObjectArrayList;

    public String getTestGroupName() {
        return testGroupName;
    }

    public void setTestGroupName(String testGroupName) {
        this.testGroupName = testGroupName;
    }

    public ArrayList<TestResultObject> getTestResultObjectArrayList() {
        return testResultObjectArrayList;
    }

    public void setTestResultObjectArrayList(ArrayList<TestResultObject> testResultObjectArrayList) {
        this.testResultObjectArrayList = testResultObjectArrayList;
    }
}
