package com.ort.profesionalinvoicemanager.model.tax;

import com.ort.profesionalinvoicemanager.model.base.PersistentObject;

import java.util.Date;

public class TaxInformation extends PersistentObject {
    private String iibb;
    private String documentNumber;
    private DocumentType documentType;
    private IvaCategory iva;
    private MonotributoCategory monotributoCategory;

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
