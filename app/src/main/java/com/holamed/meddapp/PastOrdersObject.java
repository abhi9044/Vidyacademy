package com.holamed.meddapp;

import java.util.Date;

/**
 * Created by Era on 5/19/2015.
 */
public class PastOrdersObject {
    private String tests;
    private String date;
    private String center;
    private String address;
    private String coupon_code;
    private String amount;
    private String patientName;
    private String discount;
    private String choice;
    private String phone;
    private String email;
    private String patient_id;
    private String testgroup_id;

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getTestgroup_id() {
        return testgroup_id;
    }

    public void setTestgroup_id(String testgroup_id) {
        this.testgroup_id = testgroup_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getTests() {
        return tests;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getDateString() {
        return date;
    }

    public void setDateString(String date) {
        this.date = date;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
