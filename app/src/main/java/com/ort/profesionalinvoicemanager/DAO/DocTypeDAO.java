package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
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

    @Override
    protected String getTableNameForModel() {
        return DocumentType.TABLE;
    }

    @Override
    protected DocumentType mapFromCursor(Cursor c) {
        DocumentType documentType = new DocumentType();
        documentType.setCode(Integer.parseInt(c.getString(c.getColumnIndex(DocumentType.KEY_CODE))));
        documentType.setOid(c.getString(c.getColumnIndex(DocumentType.KEY_OID)));
        documentType.setDescription(c.getString(c.getColumnIndex(DocumentType.KEY_DESCRIPTION)));
        return documentType;
    }

}
