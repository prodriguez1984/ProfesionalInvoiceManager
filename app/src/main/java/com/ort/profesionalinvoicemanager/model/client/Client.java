package com.ort.profesionalinvoicemanager.model.client;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.base.PersistentObjectWithLogicalDeletion;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;

import java.util.ArrayList;

public class Client extends PersistentObjectWithLogicalDeletion {

    private TaxInformation taxInformation;
    private String name;
    private String lastName;
    private String mail;
    private String address;

    public static final String TABLE="CLIENT";
    public static final String KEY_NAME ="NAME";
    public static final String KEY_LAST_NAME ="LAST_NAME";
    public static final String KEY_MAIL ="MAIL";
    public static final String KEY_ADDRESS ="ADDRESS";
    public static final String KEY_TAX_INFORMATION ="TAX_INFORMATION_OID";

    public Client(){
        super();
    }

    public Client(String name, String lastName, String adress, String mail){
        this();
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.address = adress;

    }

    public Client(String name, String lastName, String adress, String mail, TaxInformation tax){
        this();
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.address = adress;
        this.taxInformation = tax;
    }

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        ArrayList<PersistentField> fields=new ArrayList<>();
        fields.add (new PersistentField(KEY_NAME, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_LAST_NAME, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_ADDRESS, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_MAIL, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_TAX_INFORMATION, SQLiteDateType.TEXT,false));
        return fields;

    }

    @Override
    protected ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_NAME,getName());
        values.put(KEY_LAST_NAME,getLastName());
        values.put(KEY_MAIL,getMail());
        values.put(KEY_ADDRESS,getAddress());
        values.put(KEY_TAX_INFORMATION,getTaxInformation().getOid());
        return values;
    }

    public boolean isActive(){
        return active.intValue()==1;
    }
}
