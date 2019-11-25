package com.ort.profesionalinvoicemanager.model.tax;

import android.content.ContentValues;

import com.ort.profesionalinvoicemanager.model.base.PersistentObjectWithLogicalDeletion;
import com.ort.profesionalinvoicemanager.model.base.SQLiteDateType;

import java.util.ArrayList;

public class TaxInformation extends PersistentObjectWithLogicalDeletion {
    public final String KEY_IIBB = "IIBB";
    public final String KEY_DOCUMENT_NUMBER = "DOCUMENT_NUMBER";
    public final String KEY_DOCUMENT_TYPE = "DOCUMENT_TYPE";
    public final String KEY_IVA = "IVA";
    public final String KEY_MONOTRIBUTO = "MONOTRIBUTO";

    public final String KEY_IIBB = "IIBB";
    public final String KEY_DOCUMENT_NUMBER = "DOCUMENT_NUMBER";
    public final String KEY_DOCUMENT_TYPE_OID = "DOCUMENT_TYPE_OID";
    public final String KEY_IVA_OID = "IVA_OID";
    public final String KEY_MONOTRIBUTO_OID = "MONOTRIBUTO_OID";

    private String iibb;
    private String documentNumber;
    private DocumentType documentType;
    private IvaCategory iva;
    private MonotributoCategory monotributoCategory;

    public TaxInformation(){super();}

    public TaxInformation(String oid) {
        super.setOid(oid);
    }

    /**
     *
     * @param iibb
     * @param documentNumber
     * @param documentType
     * @param iva
     * @param monotributoCategory
     */
    public TaxInformation(String iibb, String documentNumber, DocumentType documentType, IvaCategory iva, MonotributoCategory monotributoCategory) {

        this.iibb = iibb;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.iva = iva;
        this.monotributoCategory = monotributoCategory;
    }

    @Override
    public String toString() {
        return "TaxInformation{" +
                "Ingresos Brutos='" + iibb + '\'' +
                ", Tipo de Documento=" + documentType +
                ", Número='" + documentNumber + '\'' +
                '}';
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
        fields.add(new PersistentField(KEY_DOCUMENT_TYPE_OID, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_IVA_OID, SQLiteDateType.TEXT, true));
        fields.add(new PersistentField(KEY_MONOTRIBUTO_OID, SQLiteDateType.TEXT, true));
        return fields;
    }

    @Override
    protected ContentValues toParticularContentValues(ContentValues values) {
        values.put(KEY_IIBB, getIibb());
        values.put(KEY_DOCUMENT_NUMBER, getDocumentNumber());
        values.put(KEY_DOCUMENT_TYPE_OID, getDocumentType().getOid());
        values.put(KEY_IVA_OID, getIva().getOid());
        values.put(KEY_MONOTRIBUTO_OID, getMonotributoCategory().getOid());
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
