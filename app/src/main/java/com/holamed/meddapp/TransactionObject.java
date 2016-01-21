package com.holamed.meddapp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Era on 7/15/2015.
 */
public class TransactionObject {
    String lab_name;
    String date;
    String test_groups;
    String patient_name;
    //ArrayList<TestGroupObject> testGroupObjectArrayList;
    ArrayList<String> testGroupNameArrayList;
    HashMap<String, ArrayList<TestResultObject>> hashMap;

    /*public void setTestGroupObjectArrayList(ArrayList<TestGroupObject> testGroupObjectArrayList) {
        this.testGroupObjectArrayList = testGroupObjectArrayList;
    }
*/
    public ArrayList<String> getTestGroupNameArrayList() {
        return testGroupNameArrayList;
    }

    public void setTestGroupNameArrayList(ArrayList<String> testGroupNameArrayList) {
        this.testGroupNameArrayList = testGroupNameArrayList;
    }

    public HashMap<String, ArrayList<TestResultObject>> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, ArrayList<TestResultObject>> hashMap) {
        this.hashMap = hashMap;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    /*public ArrayList<TestGroupObject> getTestGroupObjectArrayList() {
        return testGroupObjectArrayList;
    }*/

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTest_groups() {
        return test_groups;
    }

    public void setTest_groups(String test_groups) {
        this.test_groups = test_groups;
    }
}
