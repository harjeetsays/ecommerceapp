package com.example.ecommerceapp;

public class StoringCustomerData {
    String fullname,email,phonenumber,password,address;

    public StoringCustomerData(){

    }
public StoringCustomerData(String fullname, String email, String phonenumber, String password,String address){
        this.fullname =fullname;
        this.email=email;
        this.phonenumber=phonenumber;
        this.password=password;
        this.address = address;

}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
