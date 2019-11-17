package com.ort.profesionalinvoicemanager.model.product;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Product extends PersistentObject {

    public static final String TABLE="PRODUCT";

    public static final String IDENTIFICATOR_PRODUCT="Producto";
    public static final String IDENTIFICATOR_SERVICE="Servicio";

    public static final String KEY_NAME ="NAME";
    public static final String KEY_DESCRIPTION ="DESCRIPTION";
    public static final String KEY_CODE ="CODE";
    public static final String KEY_PRICE ="PRICE";
    public static final String KEY_UNIT_OID ="UNIT_OID";
    public static final String KEY_PRODUCT_TYPE ="PRODUCT_TYPE";

    private String name;
    private String description;
    private String code;
    private Double price;
    private Unit unit;
    private String productType;

    public Product(){
        super();
    }

    public Product(String name, String description, String code, Double price, Unit unit, String productType) {
        this();
        this.name = name;
        this.description = description;
        this.code = code;
        this.price = price;
        this.unit = unit;
        this.productType = productType;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        ArrayList<PersistentField> fields=new ArrayList<PersistentField>();
        fields.add (new PersistentField(KEY_NAME, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_DESCRIPTION, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_CODE, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_PRICE, SQLiteDateType.REAL,true));
        fields.add (new PersistentField(KEY_UNIT_OID, SQLiteDateType.TEXT,true));
        fields.add (new PersistentField(KEY_PRODUCT_TYPE, SQLiteDateType.TEXT,true));

        return fields;
    }

    @Override
    protected ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_NAME,getName());
        values.put(KEY_DESCRIPTION,getDescription());
        values.put(KEY_CODE,getCode());
        values.put(KEY_PRICE,getPrice());
        values.put(KEY_UNIT_OID,getUnit().getOid());
        values.put(KEY_PRODUCT_TYPE,getProductType());

        return values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
