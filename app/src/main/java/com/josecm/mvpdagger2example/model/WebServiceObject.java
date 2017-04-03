package com.josecm.mvpdagger2example.model;

/**
 * Created by JoseFelix on 27/03/2017.
 */

public class WebServiceObject {

    String zipcode;
    String item;
    String farmer_id;
    String category;

    public WebServiceObject(String zipcode, String item, String farmer_id, String category) {
        this.zipcode = zipcode;
        this.item = item;
        this.farmer_id = farmer_id;
        this.category = category;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getItem() {
        return item;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public String getCategory() {
        return category;
    }
}
