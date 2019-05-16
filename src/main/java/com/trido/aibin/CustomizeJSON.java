package com.trido.aibin;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class CustomizeJSON<T> {
    private List<Product> products;
    private int user_id;

    public CustomizeJSON() {
    }

    public CustomizeJSON(List<Product> products, int user_id) {
        this.products = products;
        this.user_id = user_id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "CustomizeJSON{" +
                "products=" + products +
                ", user_id=" + user_id +
                '}';
    }
}
