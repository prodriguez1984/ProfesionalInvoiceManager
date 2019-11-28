package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;

public class TaxInformationDAO extends AbstractDao {
    private final String TABLE = "TAX_INFORMATION";

    private static TaxInformationDAO instance;

    public static TaxInformationDAO getInstance() {
        if (instance == null) {
            instance = new TaxInformationDAO();
        }
        return instance;
    }

    @Override
    protected String getTableNameForModel() {
        return TABLE;
    }

    @Override
    protected TaxInformation mapFromCursor(Cursor c) {
        TaxInformation taxInformation = new TaxInformation();
        taxInformation.setIibb(c.getString(c.getColumnIndex(taxInformation.KEY_IIBB)));
        taxInformation.setDocumentNumber(c.getString(c.getColumnIndex(taxInformation.KEY_DOCUMENT_NUMBER)));
        taxInformation.setDocumentType(new DocumentType(c.getString(c.getColumnIndex(taxInformation.KEY_DOCUMENT_TYPE))));
        taxInformation.setIva(new IvaCategory(c.getString(c.getColumnIndex(taxInformation.KEY_IVA))));
        taxInformation.setMonotributoCategory(new MonotributoCategory(c.getString(c.getColumnIndex(taxInformation.KEY_MONOTRIBUTO))));
        return taxInformation;
    }

    public TaxInformation getCompleteTaxInformationByOid(String oid) {
        TaxInformation taxInformation = getByOid(oid);
        taxInformation.setDocumentType(DocTypeDAO.getInstance().getByOid(taxInformation.getDocumentType().getOid()));
        taxInformation.setIva(IvaCategoryDAO.getInstance().getByOid(taxInformation.getIva().getOid()));
        if (taxInformation.getMonotributoCategory() != null&&taxInformation.getMonotributoCategory().getOid()!=null){
            taxInformation.setMonotributoCategory(MonotributoCategoryDAO.getInstance().getByOid(taxInformation.getMonotributoCategory().getOid()));
    }
        return taxInformation;
    }
}
