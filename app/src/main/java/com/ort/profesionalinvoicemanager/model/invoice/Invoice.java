package com.ort.profesionalinvoicemanager.model.invoice;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;

import java.util.ArrayList;
import java.util.Date;

public class Invoice extends PersistentObject {
    public static final String TABLE ="INVOICE";
    public static final String KEY_LETTER = "LETTER";
    public static final String KEY_INVOICE_NUMBER = "INVOICE_NUMBER";
    public static final String KEY_CAE = "CAE";
    public static final String KEY_PAYMENT_CONDITION = "PAYMENT_CONDITION_OID";
    public static final String KEY_CLIENT = "CLIENT_OID";
    public static final String KEY_SINCE = "SINCE";
    public static final String KEY_UNTIL = "UNTIL";
    public static final String KEY_DUE_DATE = "DUE_DATE";
    public static final String KEY_INVOICE_DATE = "INVOICE_DATE";
    public static final String KEY_INDUSTRY = "INDUSTRY_OID";
    public static final String KEY_AMOUNT = "AMOUNT";
    public static final String KEY_DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
    public static final String KEY_DISCOUNT_RATE = "DISCOUNT_RATE";
    public static final String KEY_NET_AMOUNT = "NET_AMOUNT";

    private Client client;
    private PaymentCondition paymentCondition;
    private Date invoiceDate;
    private Date invoiceSince;
    private Date invoiceUntil;
    private Date dueDate;
    private String letter;
    private Industry industry;
    private Integer invoiceNumber;
    private Integer CAE;
    private Double amount;
    private Double discountAmount;
    private Double discountRate;
    private Double netAmount;
    private ArrayList<InvoiceDetail> details;

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        ArrayList<PersistentField> fields=new ArrayList<>();
        fields.add (new PersistentField(KEY_LETTER           , SQLiteDateType.TEXT,false));
        fields.add (new PersistentField(KEY_INVOICE_NUMBER   , SQLiteDateType.NUMERIC,false));
        fields.add (new PersistentField(KEY_CAE              , SQLiteDateType.NUMERIC,false));
        fields.add (new PersistentField(KEY_PAYMENT_CONDITION, SQLiteDateType.TEXT,false));
        fields.add (new PersistentField(KEY_CLIENT           , SQLiteDateType.TEXT,false));
        fields.add (new PersistentField(KEY_SINCE            , SQLiteDateType.TEXT,false));
        fields.add (new PersistentField(KEY_UNTIL            , SQLiteDateType.TEXT,false));
        fields.add (new PersistentField(KEY_DUE_DATE         , SQLiteDateType.TEXT,false));
        fields.add (new PersistentField(KEY_INVOICE_DATE     , SQLiteDateType.TEXT,false));
        fields.add (new PersistentField(KEY_INDUSTRY         , SQLiteDateType.TEXT,false));
        fields.add (new PersistentField(KEY_AMOUNT           , SQLiteDateType.REAL,false));
        fields.add (new PersistentField(KEY_DISCOUNT_AMOUNT  , SQLiteDateType.REAL,false));
        fields.add (new PersistentField(KEY_DISCOUNT_RATE    , SQLiteDateType.REAL,false));
        fields.add (new PersistentField(KEY_NET_AMOUNT       , SQLiteDateType.REAL,false));
        return fields;
    }

    @Override
    public ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_LETTER           ,getLetter() );
        values.put(KEY_INVOICE_NUMBER   , getInvoiceNumber());
        values.put(KEY_CAE              , getCAE());
        values.put(KEY_PAYMENT_CONDITION, getPaymentCondition().getOid());
        values.put(KEY_CLIENT           , getClient().getOid());
        values.put(KEY_SINCE            , getInvoiceSince().toString());
        values.put(KEY_UNTIL            ,getInvoiceUntil().toString() );
        values.put(KEY_DUE_DATE         , getDueDate().toString());
        values.put(KEY_INVOICE_DATE     , getInvoiceDate().toString());
        values.put(KEY_INDUSTRY         , getIndustry().getOid());
        values.put(KEY_AMOUNT           , getAmount());
        values.put(KEY_DISCOUNT_AMOUNT  , getDiscountAmount());
        values.put(KEY_DISCOUNT_RATE    , getDiscountRate());
        values.put(KEY_NET_AMOUNT       , getNetAmount());
        return values;
    }

    public Invoice() {
    }

    /**
     *
     * @param client
     * @param paymentCondition
     * @param invoiceDate
     * @param invoiceSince
     * @param invoiceUntil
     * @param dueDate
     * @param letter
     * @param industry
     * @param invoiceNumber
     * @param CAE
     * @param amount
     * @param discountAmount
     * @param discountRate
     * @param netAmount
     */
    public Invoice(Client client, PaymentCondition paymentCondition, Date invoiceDate, Date invoiceSince, Date invoiceUntil, Date dueDate, String letter, Industry industry, Integer invoiceNumber, Integer CAE, Double amount, Double discountAmount, Double discountRate, Double netAmount) {
        this.client = client;
        this.paymentCondition = paymentCondition;
        this.invoiceDate = invoiceDate;
        this.invoiceSince = invoiceSince;
        this.invoiceUntil = invoiceUntil;
        this.dueDate = dueDate;
        this.letter = letter;
        this.industry = industry;
        this.invoiceNumber = invoiceNumber;
        this.CAE = CAE;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.netAmount = netAmount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PaymentCondition getPaymentCondition() {
        return paymentCondition;
    }

    public void setPaymentCondition(PaymentCondition paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceSince() {
        return invoiceSince;
    }

    public void setInvoiceSince(Date invoiceSince) {
        this.invoiceSince = invoiceSince;
    }

    public Date getInvoiceUntil() {
        return invoiceUntil;
    }

    public void setInvoiceUntil(Date invoiceUntil) {
        this.invoiceUntil = invoiceUntil;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getCAE() {
        return CAE;
    }

    public void setCAE(Integer CAE) {
        this.CAE = CAE;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
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

    public ArrayList<InvoiceDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<InvoiceDetail> details) {
        this.details = details;
    }
}
