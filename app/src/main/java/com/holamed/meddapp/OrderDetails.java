package com.holamed.meddapp;

/**
 * Created by Era on 5/19/2015.
 */
public class OrderDetails {

    private static  String orderID;
    private static  String tests;
    private static  String amount;
    private static  String centerAddress;
    private static  String centerName;
    private static  String couponCode;

    public String getOrderID(){
        return orderID;
    }


    public String getTests(){
        return tests;
    }
    public String getAmount(){
        return amount;
    }
    public String getCenterAddress(){
        return centerAddress;
    }
    public String getCouponCode(){
        return couponCode;
    }
    public String getCenterName(){
        return centerName;
    }

    public static void setAmount(String amount) {
        OrderDetails.amount = amount;
    }

    public static void setCenterAddress(String centerAddress) {
        OrderDetails.centerAddress = centerAddress;
    }

    public static void setCenterName(String centerName) {
        OrderDetails.centerName = centerName;
    }

    public static void setCouponCode(String couponCode) {
        OrderDetails.couponCode = couponCode;
    }

    public static void setOrderID(String orderID) {
        OrderDetails.orderID = orderID;
    }

    public static void setTests(String tests) {
        OrderDetails.tests = tests;
    }

}

