package com.ort.profesionalinvoicemanager.model.user;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;
import com.ort.profesionalinvoicemanager.model.industry.Industry;

import java.util.ArrayList;

public class User extends PersistentObject {

    private final String KEY_USER ="USER_NAME";
    private final String KEY_PASS ="PASSWORD";
    private final String KEY_MAIL ="MAIL";
    private final String KEY_INDUSTRY ="INDUSTRY_OID";

    private String userName;
    private String password;
    private String mail;
    private Industry industry;

    public User (){
        super();
    }

    @Override
    public String getTableName() {
        return "USER";
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        ArrayList<PersistentField> fields=new ArrayList<>();
        fields.add (new PersistentField(KEY_USER, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_PASS, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_MAIL, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_INDUSTRY, SQLiteDateType.TEXT,true));
        return fields;
    }

    @Override
    public ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_USER,getUserName());
        values.put(KEY_PASS,getPassword());
        values.put(KEY_MAIL,getMail());
        values.put(KEY_INDUSTRY,getIndustry().getOid());
        return values;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }
}
