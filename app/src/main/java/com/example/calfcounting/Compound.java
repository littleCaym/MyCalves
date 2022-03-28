package com.example.calfcounting;

import java.util.Date;

public class Compound {

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String SELLER = "SELLER";
    public static final String RATING = "RATING";
    public static final String UPLOAD_ADVERT_DATE = "UPLOAD_ADVERT_DATE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String LINK_TO_ADVERT = "LINK_TO_ADVERT";
    public static final String PRICE = "PRICE";
    public static final String CONNECTION_TIME = "CONNECTION_TIME";
    private long id;
    private String name ="";
    private String seller ="";
    private float rating;
    private String upload_advert_date ="";
    private String description ="";
    private String link_to_advert ="";
    private float price;
    private java.util.Date connection_time;

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

    public String getUpload_advert_date() {
        return upload_advert_date;
    }

    public void setUpload_advert_date(String upload_advert_date) {
        this.upload_advert_date = upload_advert_date;
    }

    public Date getConnection_time() {
        return connection_time;
    }

    public void setConnection_time(Date connection_time) {
        this.connection_time = connection_time;
    }
}
