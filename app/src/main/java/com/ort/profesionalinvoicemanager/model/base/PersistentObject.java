package com.ort.profesionalinvoicemanager.model.base;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class PersistentObject {
    private static String KEY_OID="OID";
    private static String KEY_CREATION_TIMESTAMP="CREATION_TIMESTAMP";
    private static String KEY_MODIFICATION_TIMESTAMP="MODIFICATION_TIMESTAMP";
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
        ContentValues values=new ContentValues();
        values.put(KEY_OID,oid);
        if (creationTimestamp==null){
            creationTimestamp=new Date();
        }
        values.put(KEY_CREATION_TIMESTAMP,creationTimestamp.toString());
        if (modificationTimestamp!=null) {
            values.put(KEY_MODIFICATION_TIMESTAMP, modificationTimestamp.toString());
        }
        return toParticularContentValues(values);
    }

    public String getCreationScript() {
        StringBuffer query=new StringBuffer();
        query.append("CREATE TABLE ");
        query.append(getTableName());
        query.append(" (OID  TEXT, CREATION_TIMESTAMP TEXT, MODIFICATION_TIMESTAMP TEXT, ");
        for (PersistentField field:getFieldsForTableCreation()){
            query.append(getFieldStringForCreation(field.getFieldName(),field.getType(),field.isManadatory));
        }
        query.append(" UNIQUE (OID))");

        return query.toString();
    }

    private String getFieldStringForCreation(String fieldName,SQLiteDateType type,boolean mandatory){
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
}
