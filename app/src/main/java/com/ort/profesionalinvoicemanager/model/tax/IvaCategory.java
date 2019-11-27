package com.ort.profesionalinvoicemanager.model.tax;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;

import java.util.ArrayList;

public class IvaCategory extends PersistentObject {
    public static final String TABLE ="IVA_CATEGORY";
    public static final String KEY_CODE ="CODE";
    public static final String KEY_DESCRIPTION ="DESCRIPTION";

    private Integer code;
    private String description;

    public IvaCategory() {
        super();
    }

    public IvaCategory(Integer code, String description) {
        this();
        this.code = code;
        this.description = description;
    }

    public IvaCategory(String oidIva) {
        this.setOid(oidIva);
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        ArrayList<PersistentField> fields=new ArrayList<>();
        fields.add (new PersistentField(KEY_CODE, SQLiteDateType.INTEGER,true));
        fields.add (new PersistentField(KEY_DESCRIPTION, SQLiteDateType.TEXT,true));
        return fields;
    }

    @Override
    public String toString() {
        return "Iva{" +
                "Categoría='" + description + '\'' +
                '}';
    }

    @Override
    protected ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_CODE,getCode());
        values.put(KEY_DESCRIPTION,getDescription());
        return values;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
