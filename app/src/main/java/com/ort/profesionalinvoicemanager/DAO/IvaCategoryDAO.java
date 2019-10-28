package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
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

    public ArrayList<IvaCategory> getAll() {
        ArrayList<IvaCategory> lstIva = new ArrayList<>();
        Cursor c = executeSqlQuery("Select * FROM " + TABLE, null);
        if (c.getCount() == 0) {
            return null;
        }
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                IvaCategory ivaCategory = new IvaCategory();
                ivaCategory.setOid(c.getString(c.getColumnIndex(ivaCategory.KEY_OID)));
                ivaCategory.setDescription(c.getString(c.getColumnIndex(ivaCategory.KEY_DESCRIPTION)));
                lstIva.add(ivaCategory);
                c.moveToNext();
            }
        }

        return lstIva;
    }
}
