package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;

import java.util.ArrayList;

public class IvaCategoryDAO extends AbstractDao {
    private final String TABLE = "IVA_CATEGORY";
    private static IvaCategoryDAO instance;

    private IvaCategoryDAO() {
        super();
    }

    public static IvaCategoryDAO getInstance() {
        if (instance == null) {
            instance = new IvaCategoryDAO();
        }
        return instance;
    }

    @Override
    protected String getTableNameForModel() {
        return IvaCategory.TABLE;
    }

    @Override
    protected IvaCategory mapFromCursor(Cursor c) {
        IvaCategory ivaCategory = new IvaCategory();
        ivaCategory.setOid(c.getString(c.getColumnIndex(ivaCategory.KEY_OID)));
        ivaCategory.setDescription(c.getString(c.getColumnIndex(ivaCategory.KEY_DESCRIPTION)));
        return ivaCategory;
    }
}
