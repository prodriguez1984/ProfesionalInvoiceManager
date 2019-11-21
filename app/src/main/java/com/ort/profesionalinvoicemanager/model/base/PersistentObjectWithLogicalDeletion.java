package com.ort.profesionalinvoicemanager.model.base;

import android.content.ContentValues;

public abstract class PersistentObjectWithLogicalDeletion extends PersistentObject {
    public static String KEY_ACTIVE="ACTIVE";
    public Integer active;

    public PersistentObjectWithLogicalDeletion() {
        super();
    }

    @Override
    protected ContentValues initializeContentValues() {
        ContentValues values=super.initializeContentValues();
        if (active==null){
            active=new Integer(1);
        }
        values.put(KEY_ACTIVE,active);
        return values;
    }

    @Override
    public StringBuffer initializeCreationQuery() {
        StringBuffer query=super.initializeCreationQuery();
        query.append(getFieldStringForCreation(KEY_ACTIVE,SQLiteDateType.INTEGER,true));
        return query;
    }

    @Override
    protected boolean physicalDelete() {
        return false;
    }

    public boolean isActive(){
        return active.intValue()==1;
    }

}
