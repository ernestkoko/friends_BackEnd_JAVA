package com.wiredbrain.friends.model;

import javax.persistence.Embeddable;

@Embeddable  //shows that we can use it inside another class that has @Entity
public class Address {
    private String street;
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
