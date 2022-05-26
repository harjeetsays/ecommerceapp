package com.example.ecommerceapp.models;

public class ModelShop {
    String uid,email,paswrd, address, phonenumber,city,shopname,latitude,longitude;

    public ModelShop() {
    }

    public ModelShop(String uid,String email,String paswrd ,String address, String phonenumber, String city, String shopname, String latitude, String longitude) {
        this.uid= uid;
        this.email = email;
        this.paswrd= paswrd;
        this.address = address;
        this.phonenumber = phonenumber;
        this.city = city;
        this.shopname = shopname;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPaswrd() {
        return paswrd;
    }

    public void setPaswrd(String paswrd) {
        this.paswrd = paswrd;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
