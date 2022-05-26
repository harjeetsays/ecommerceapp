package com.example.ecommerceapp;

public class StoringSellerData {
    String shopname,phonenumber,paswrd,email,address;

    public StoringSellerData() {
    }

    public StoringSellerData(String shopname, String phonenumber, String paswrd,String email,String address) {
        this.shopname = shopname;
        this.address= address;
        this.phonenumber = phonenumber;
        this.paswrd = paswrd;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }



    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPaswrd() {
        return paswrd;
    }

    public void setPaswrd(String paswrd) {
        this.paswrd = paswrd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
