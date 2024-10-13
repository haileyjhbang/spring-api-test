package com.boot.api.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
    @JsonProperty("customer_id")
    private String customerId;
    private String name;
    private CustomerStatus status;


    public static enum CustomerStatus {
        active, dormant;
    }


    public Customer() {
    }


    public String getCustomerId() {
        return customerId;
    }


    public String getName() {
        return name;
    }


    public CustomerStatus getStatus() {
        return status;
    }
}
