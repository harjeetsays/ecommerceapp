package com.example.ecommerceapp.models;

public class ModelProduct {
   private String PId,Title,Description,Category,Quantity,Price,Img,TimeStamp,Uid;

    public ModelProduct() {
    }

    public ModelProduct(String PId, String title, String description, String category, String quantity, String price, String img, String timeStamp, String uid) {
        this.PId = PId;
        Title = title;
        Description = description;
        Category = category;
        Quantity = quantity;
        Price = price;
        Img = img;
        TimeStamp = timeStamp;
        Uid = uid;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
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

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
