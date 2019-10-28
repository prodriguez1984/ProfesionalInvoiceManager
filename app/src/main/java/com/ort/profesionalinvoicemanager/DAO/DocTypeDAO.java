package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;

import java.util.ArrayList;
import java.util.List;

public class DocTypeDAO extends AbstractDao {
    private static DocTypeDAO instance;

    private DocTypeDAO() {
        super();
    }

    public static DocTypeDAO getInstance() {
        if (instance == null) {
            instance = new DocTypeDAO();
        }
        return instance;
    }


    public ArrayList<DocumentType> getAll() {
        ArrayList<DocumentType> lstDocs = new ArrayList<>();
        Cursor c = executeSqlQuery("Select * from DOCUMENT_TYPE ", null);
        if (c.getCount() == 0) {
            return null;
        }
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                DocumentType documentType = new DocumentType();
                documentType.setOid(c.getString(c.getColumnIndex(DocumentType.KEY_OID)));
                documentType.setDescription(c.getString(c.getColumnIndex(DocumentType.KEY_DESCRIPTION)));
                lstDocs.add(documentType);
                c.moveToNext();
            }
        }

        return lstDocs;
    }

}
