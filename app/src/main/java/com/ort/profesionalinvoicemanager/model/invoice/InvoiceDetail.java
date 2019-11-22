package com.ort.profesionalinvoicemanager.model.invoice;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;
import com.ort.profesionalinvoicemanager.model.product.Product;

import java.util.ArrayList;

public class InvoiceDetail extends PersistentObject {
    private final String TABLE = "INVOICE_DETAIL";
    private final String KEY_PRODUCT = "INDUSTRY_OID";
    private final String KEY_AMOUNT = "AMOUNT";
    private final String KEY_QUANTITY= "QUANTITY";
    private final String KEY_DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
    private final String KEY_DISCOUNT_RATE = "DISCOUNT_RATE";
    private final String KEY_NET_AMOUNT = "NET_AMOUNT";

    private Double amount;
    private Double discountAmount;
    private Double discountRate;
    private Double netAmount;
    private Integer quantity;
    private Product product;

    public InvoiceDetail(Double amount, Double discountAmount, Double discountRate, Double netAmount, Integer quantity, Product product) {
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.netAmount = netAmount;
        this.quantity = quantity;
        this.product = product;
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        ArrayList<PersistentField> fields=new ArrayList<>();
        fields.add (new PersistentField(KEY_PRODUCT         , SQLiteDateType.TEXT,false));
        fields.add (new PersistentField(KEY_QUANTITY         , SQLiteDateType.INTEGER,false));
        fields.add (new PersistentField(KEY_AMOUNT           , SQLiteDateType.REAL,false));
        fields.add (new PersistentField(KEY_DISCOUNT_AMOUNT  , SQLiteDateType.REAL,false));
        fields.add (new PersistentField(KEY_DISCOUNT_RATE    , SQLiteDateType.REAL,false));
        fields.add (new PersistentField(KEY_NET_AMOUNT       , SQLiteDateType.REAL,false));
        return fields;
    }

    @Override
    protected ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_AMOUNT           , getAmount());
        values.put(KEY_DISCOUNT_AMOUNT  , getDiscountAmount());
        values.put(KEY_DISCOUNT_RATE    , getDiscountRate());
        values.put(KEY_NET_AMOUNT       , getNetAmount());
        values.put(KEY_QUANTITY       , getQuantity());
        values.put(KEY_PRODUCT       , getProduct().getOid());
        return values;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
