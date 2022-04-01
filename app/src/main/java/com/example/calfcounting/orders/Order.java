package com.example.calfcounting.orders;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Order implements Parcelable {

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String SELLER = "SELLER";
    public static final String RATING = "RATING";
    public static final String REVIEWS_NUM = "REVIEWS_NUM";
    public static final String UPLOAD_ADVERT_DATE = "UPLOAD_ADVERT_DATE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String LOCATION = "LOCATION";
    public static final String LINK_TO_ADVERT = "LINK_TO_ADVERT";
    public static final String PRICE = "PRICE";
    public static final String DATE_ADDED = "DATE_ADDED";
    public static final String AMOUNT = "AMOUNT";
    public static final String DATE_OF_ARRIVAL = "DATE_OF_ARRIVAL";
    public static final String STATUS = "STATUS";

    private long id;
    private String name;
    private String seller;
    private float rating;
    private int reviews_num;
    private String upload_advert_date;
    private String description;
    private String location;
    private String link_to_advert;
    private float price;
    private java.util.Date date_added;
    private float amount;
    private java.util.Date date_of_arrival;
    private int status; //0 - добавлен в список 1 - в пути 2 - прибыл 3 - отложен 4 - отменен

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(seller);
        dest.writeFloat(rating);
        dest.writeInt(reviews_num);
        dest.writeString(upload_advert_date);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(link_to_advert);
        dest.writeFloat(price);
        dest.writeLong(date_added.getTime());
        dest.writeFloat(amount);
        dest.writeLong(date_of_arrival.getTime());
        dest.writeInt(status);
    }

    private Order(Parcel source) {
        id = source.readLong();
        name = source.readString();
        seller = source.readString();
        rating = source.readFloat();
        reviews_num = source.readInt();
        upload_advert_date = source.readString();
        description = source.readString();
        location = source.readString();
        link_to_advert = source.readString();
        price = source.readFloat();
        date_added = new java.util.Date(source.readLong());
        amount = source.readFloat();
        date_of_arrival = new java.util.Date(source.readLong());
        status = source.readInt();
    }


    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUpload_advert_date() {
        return upload_advert_date;
    }

    public void setUpload_advert_date(String upload_advert_date) {
        this.upload_advert_date = upload_advert_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink_to_advert() {
        return link_to_advert;
    }

    public void setLink_to_advert(String link_to_advert) {
        this.link_to_advert = link_to_advert;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate_of_arrival() {
        return date_of_arrival;
    }

    public void setDate_of_arrival(Date date_of_arrival) {
        this.date_of_arrival = date_of_arrival;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReviews_num() {
        return reviews_num;
    }

    public void setReviews_num(int reviews_num) {
        this.reviews_num = reviews_num;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatusString() {
        switch (status){
            case 0: return "Добавлен в заказы";
            case 1: return "В пути";
            case 2: return "Прибыл";
            case 3: return "Отложен";
            case 4: return "Отменен";
            default: return "Не указан";
        }

    }

}
