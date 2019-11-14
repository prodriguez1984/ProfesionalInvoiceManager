package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;

import java.util.ArrayList;

public class MonotributoCategoryDAO extends AbstractDao {
    private final String TABLE = "MONOTRIBUTO_CATEGORY";
    private static MonotributoCategoryDAO instance;

    private MonotributoCategoryDAO() {
        super();
    }

    public static MonotributoCategoryDAO getInstance() {
        if (instance == null) {
            instance = new MonotributoCategoryDAO();
        }
        return instance;
    }

    public ArrayList<MonotributoCategory> getAll() {
        ArrayList<MonotributoCategory> lstDocs = new ArrayList<>();
        Cursor c = executeSqlQuery("Select * FROM " + TABLE, null);
        if (c.getCount() == 0) {
            return null;
        }
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                MonotributoCategory monotributoCategory = new MonotributoCategory();
                monotributoCategory.setOid(c.getString(c.getColumnIndex(MonotributoCategory.KEY_OID)));
                monotributoCategory.setCategory(c.getString(c.getColumnIndex(MonotributoCategory.KEY_CATEGORY)));
                lstDocs.add(monotributoCategory);
                c.moveToNext();
            }
        }

        return lstDocs;
    }
}
