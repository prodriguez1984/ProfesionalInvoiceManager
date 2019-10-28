package com.ort.profesionalinvoicemanager.model.tax;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MonotributoCategory extends PersistentObject {
    public static final String KEY_CATEGORY ="CATEGORY";
    public static final String KEY_MINIMUN_EMPLOYEES ="MINIMUN_EMPLOYEES";
    public static final String KEY_GROSS_AMOUNT ="GROSS_AMOUNT";
    public static final String KEY_MAX_AFFECTED_SUP ="USER_NAME";
    public static final String KEY_MAX_ELECTRICITY_CONSUMTION ="MAX_ELECTRICITY_CONSUMTION";

    private String category;
    private BigDecimal grossAmount;
    private String minimunEmployees;
    private String maxAffectedSup;
    private String maxElectricityConmsumtion;

    public MonotributoCategory(){
        super();
    }

    public MonotributoCategory(String category, BigDecimal grossAmount, String minimunEmployees, String maxAffectedSup, String maxElectricityConmsumtion) {
        this.category = category;
        this.grossAmount = grossAmount;
        this.minimunEmployees = minimunEmployees;
        this.maxAffectedSup = maxAffectedSup;
        this.maxElectricityConmsumtion = maxElectricityConmsumtion;
    }

    @Override
    public String getTableName() {
        return "MONOTRIBUTO_CATEGORY";
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        ArrayList<PersistentField> fields=new ArrayList<>();
        fields.add (new PersistentField(KEY_CATEGORY, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_GROSS_AMOUNT, SQLiteDateType.NUMERIC,true));
        fields.add (new PersistentField(KEY_MINIMUN_EMPLOYEES, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_MAX_AFFECTED_SUP, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_MAX_ELECTRICITY_CONSUMTION, SQLiteDateType.TEXT,true));

        return fields;
    }

    @Override
    protected ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_CATEGORY,getCategory());
        values.put(KEY_GROSS_AMOUNT,getGrossAmount().doubleValue());
        values.put(KEY_MINIMUN_EMPLOYEES,getMinimunEmployees());
        values.put(KEY_MAX_AFFECTED_SUP,getMaxAffectedSup());
        values.put(KEY_MAX_ELECTRICITY_CONSUMTION,getMaxElectricityConmsumtion());
        return values;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getMinimunEmployees() {
        return minimunEmployees;
    }

    public void setMinimunEmployees(String minimunEmployees) {
        this.minimunEmployees = minimunEmployees;
    }

    public String getMaxAffectedSup() {
        return maxAffectedSup;
    }

    public void setMaxAffectedSup(String maxAffectedSup) {
        this.maxAffectedSup = maxAffectedSup;
    }

    public String getMaxElectricityConmsumtion() {
        return maxElectricityConmsumtion;
    }

    public void setMaxElectricityConmsumtion(String maxElectricityConmsumtion) {
        this.maxElectricityConmsumtion = maxElectricityConmsumtion;
    }
}
