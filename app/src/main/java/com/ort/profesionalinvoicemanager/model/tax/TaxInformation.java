package com.ort.profesionalinvoicemanager.model.tax;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObjectWithLogicalDeletion;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;

import java.util.ArrayList;

public class TaxInformation extends PersistentObjectWithLogicalDeletion {
    private final String KEY_IIBB = "IIBB";
    private final String KEY_DOCUMENT_NUMBER = "DOCUMENT_NUMBER";
    private final String KEY_DOCUMENT_TYPE = "DOCUMENT_TYPE";
    private final String KEY_IVA = "IVA";
    private final String KEY_MONOTRIBUTO = "MONOTRIBUTO";

    private String iibb;
    private String documentNumber;
    private DocumentType documentType;
    private IvaCategory iva;
    private MonotributoCategory monotributoCategory;

    public TaxInformation(){super();}

    public TaxInformation(String iibb, String documentNumber, DocumentType documentType, IvaCategory iva, MonotributoCategory monotributoCategory) {
        this.iibb = iibb;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.iva = iva;
        this.monotributoCategory = monotributoCategory;
    }

    @Override
    public String getTableName() {
        return "TAX_INFORMATION";
    }

    @Override
    public ArrayList<PersistentField> getFieldsForTableCreation() {
        ArrayList<PersistentField> fields = new ArrayList<>();
        fields.add(new PersistentField(KEY_IIBB, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_DOCUMENT_NUMBER, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_DOCUMENT_TYPE, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_IVA, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_MONOTRIBUTO, SQLiteDateType.TEXT, true));
        return fields;
    }

    @Override
    protected ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_IIBB, getIibb());
        values.put(KEY_DOCUMENT_NUMBER, getDocumentNumber());
        values.put(KEY_DOCUMENT_TYPE, getDocumentType().getOid());
        values.put(KEY_IVA, getIva().getOid());
        values.put(KEY_MONOTRIBUTO, getMonotributoCategory().getOid());
        return values;
    }

    public String getIibb() {
        return iibb;
    }

    public void setIibb(String iibb) {
        this.iibb = iibb;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public IvaCategory getIva() {
        return iva;
    }

    public void setIva(IvaCategory iva) {
        this.iva = iva;
    }

    public MonotributoCategory getMonotributoCategory() {
        return monotributoCategory;
    }

    public void setMonotributoCategory(MonotributoCategory monotributoCategory) {
        this.monotributoCategory = monotributoCategory;
    }
}
