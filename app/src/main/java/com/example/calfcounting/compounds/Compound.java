package com.example.calfcounting.compounds;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class Compound implements Parcelable {

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
    public static final String CONNECTION_TIME = "CONNECTION_TIME";
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
    private Timestamp connection_time;

    public static final Creator<Compound> CREATOR = new Creator<Compound>() {
        @Override
        public Compound createFromParcel(Parcel in) {
            return new Compound(in);
        }

        @Override
        public Compound[] newArray(int size) {
            return new Compound[size];
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
        dest.writeLong(connection_time.getTime());//!!!!!!!!!!
    }

    private Compound(Parcel source) {
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
        connection_time = new Timestamp(source.readLong());
    }

    public Compound() {
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

    public Timestamp getConnection_time() {
        return connection_time;
    }

    public void setConnection_time(Timestamp connection_time) {
        this.connection_time = connection_time;
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
}
