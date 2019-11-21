package com.ort.profesionalinvoicemanager.model.base;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class PersistentObject implements Serializable {
    public static String KEY_OID="OID";
    public static String KEY_CREATION_TIMESTAMP="CREATION_TIMESTAMP";
    public static String KEY_MODIFICATION_TIMESTAMP="MODIFICATION_TIMESTAMP";
    private String oid;
    private Date creationTimestamp;
    private Date modificationTimestamp;

    public PersistentObject() {
        oid=UUID.randomUUID().toString();
    }

    public abstract String getTableName();
    public abstract ArrayList<PersistentField> getFieldsForTableCreation();


    protected abstract ContentValues toParticularContentValues(ContentValues values);

    public ContentValues toContentValues(){
        ContentValues values=initializeContentValues();
        return toParticularContentValues(values);
    }

    protected ContentValues initializeContentValues(){
        ContentValues values=new ContentValues();
        values.put(KEY_OID,oid);
        if (creationTimestamp==null){
            creationTimestamp=new Date();
        }
        if (modificationTimestamp==null) {
            modificationTimestamp=new Date();
        }
        values.put(KEY_CREATION_TIMESTAMP, AbstractDao.iso8601Format.format(creationTimestamp));
        values.put(KEY_MODIFICATION_TIMESTAMP, AbstractDao.iso8601Format.format(modificationTimestamp));
        return values;
    }

    public String getCreationScript() {
        StringBuffer query=initializeCreationQuery();
        for (PersistentField field:getFieldsForTableCreation()){
            query.append(getFieldStringForCreation(field.getFieldName(),field.getType(),field.isManadatory));
        }
        query.append(" UNIQUE (OID))");

        return query.toString();
    }

    protected StringBuffer initializeCreationQuery(){
        StringBuffer query=new StringBuffer();
        query.append("CREATE TABLE ");
        query.append(getTableName());
        query.append(" (OID  TEXT, CREATION_TIMESTAMP TEXT, MODIFICATION_TIMESTAMP TEXT, ");
        return query;
    }

    protected String getFieldStringForCreation(String fieldName,SQLiteDateType type,boolean mandatory){
        StringBuffer query=new StringBuffer();
        query.append(" ");
            query.append(fieldName);
            query.append(" ");
            query.append(type);
            if(mandatory){
                query.append(" NOT NULL");
            }
        query.append(", ");
        return query.toString();
    }

    protected boolean physicalDelete(){
        return true;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public class PersistentField {
        private String fieldName;
        private SQLiteDateType type;
        private boolean isManadatory;

        public PersistentField(String fieldName, SQLiteDateType type, boolean isManadatory) {
            this.fieldName = fieldName;
            this.type = type;
            this.isManadatory = isManadatory;
        }

        public String getFieldName() {
            return fieldName;
        }

        public SQLiteDateType getType() {
            return type;
        }

        public boolean isManadatory() {
            return isManadatory;
        }
    }

    protected void updateObject(){
        modificationTimestamp=new Date();
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }

    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }
}
