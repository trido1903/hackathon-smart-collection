package com.trido.aibin;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {
    private int id;
    private String title;
    private String description;
    private String address;
    private double lati;
    private double longi;
    private long date;
    private int user_id;
    private String images;
    private User currentUser;

    public Product() {
    }

    public Product(int id, String title, String description, String address, double lati, double longi, long date, int user_id, String images, User currentUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.lati = lati;
        this.longi = longi;
        this.date = date;
        this.user_id = user_id;
        this.images = images;
        this.currentUser = currentUser;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", lati=" + lati +
                ", longi=" + longi +
                ", date=" + date +
                ", user_id=" + user_id +
                ", images='" + images + '\'' +
                '}';
    }
}
