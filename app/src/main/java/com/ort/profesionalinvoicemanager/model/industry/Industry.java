package com.ort.profesionalinvoicemanager.model.industry;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObjectWithLogicalDeletion;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;

import java.util.ArrayList;
import java.util.Date;

public class Industry extends PersistentObjectWithLogicalDeletion {
    private final String KEY_NAME = "NAME";
    private final String KEY_ADDRESS = "ADDRESS";
    private final String KEY_MAIL = "MAIL";
    private final String KEY_TELEPHONE = "TELEPHONE";
    private final String KEY_CELLPHONE = "CELLPHONE";
    private final String KEY_ACTIVITY_START = "ACTIVITY_START";
    private final String KEY_TAX_INFORMATION = "TAX_INFORMATION";

    private String name;
    private String address;
    private String mail;
    private String telephone;
    private String cellphone;
    private Date activityStart;
    private TaxInformation taxInformation;


    @Override
    public String getTableName() {
        return "INDUSTRY";
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        ArrayList<PersistentField> fields = new ArrayList<>();
        fields.add(new PersistentField(KEY_NAME, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_ADDRESS, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_MAIL, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_TELEPHONE, SQLiteDateType.TEXT, false));
        fields.add(new PersistentField(KEY_CELLPHONE, SQLiteDateType.TEXT, false));
        fields.add(new PersistentField(KEY_ACTIVITY_START, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_TAX_INFORMATION, SQLiteDateType.TEXT, true));
        return fields;
    }

    public Industry() {
    }

    /**
     *
     * @param name
     * @param address
     * @param mail
     * @param telephone
     * @param cellphone
     * @param activityStart
     * @param taxInformation
     */
    public Industry(String name, String address, String mail, String telephone, String cellphone, Date activityStart, TaxInformation taxInformation) {

        this.name = name;
        this.address = address;
        this.mail = mail;
        this.telephone = telephone;
        this.cellphone = cellphone;
        this.activityStart = activityStart;
        this.taxInformation = taxInformation;
    }

    @Override
    protected ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_NAME, getName());
        values.put(KEY_ADDRESS, getAddress());
        values.put(KEY_MAIL, getMail());
        values.put(KEY_TELEPHONE, getTelephone());
        values.put(KEY_CELLPHONE, getCellphone());
        values.put(KEY_ACTIVITY_START, getActivityStart().toString());
        values.put(KEY_TAX_INFORMATION, getTaxInformation().getOid());

        return values;
    }



    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getMail() {
        return mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public Date getActivityStart() {
        return activityStart;
    }

    public TaxInformation getTaxInformation() {
        return taxInformation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setActivityStart(Date activityStart) {
        this.activityStart = activityStart;
    }

    public void setTaxInformation(TaxInformation taxInformation) {
        this.taxInformation = taxInformation;
    }
}
