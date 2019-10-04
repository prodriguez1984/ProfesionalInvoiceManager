package com.ort.profesionalinvoicemanager.model.client;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;

import java.util.ArrayList;

public class Client extends PersistentObject {
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

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        return null;
    }

    @Override
    protected ContentValues toParticularContentValues(ContentValues values) {
        return null;
    }
}
