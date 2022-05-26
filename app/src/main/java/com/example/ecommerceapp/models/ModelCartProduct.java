package com.example.ecommerceapp.models;

public class ModelCartProduct {
    private String PId,sID,uID,Price,Title,Quantity,Img,TimeStamp,Category;

    public ModelCartProduct() {
    }

    public ModelCartProduct(String PId, String sID, String uID, String price, String title, String quantity, String img, String timeStamp, String category) {
        this.PId = PId;
        this.sID = sID;
        this.uID = uID;
        this.Price = price;
        this.Title = title;
        this.Quantity = quantity;
        this.Img = img;
        this.TimeStamp = timeStamp;
        this.Category = category;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
