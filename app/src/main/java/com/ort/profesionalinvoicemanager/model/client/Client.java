package com.ort.profesionalinvoicemanager.model.client;

import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;

public class Client {
    private TaxInformation taxInformation;
    private String name;
    private String lastName;
    private String address;

    public TaxInformation getTaxInformation() {
        return taxInformation;
    }

    public void setTaxInformation(TaxInformation taxInformation) {
        this.taxInformation = taxInformation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
