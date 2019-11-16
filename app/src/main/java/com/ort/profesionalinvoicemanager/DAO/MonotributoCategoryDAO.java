package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
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

    @Override
    protected String getTableNameForModel() {
        return MonotributoCategory.TABLE;
    }

    @Override
    protected MonotributoCategory mapFromCursor(Cursor c) {
        MonotributoCategory monotributoCategory = new MonotributoCategory();
        monotributoCategory.setOid(c.getString(c.getColumnIndex(MonotributoCategory.KEY_OID)));
        monotributoCategory.setCategory(c.getString(c.getColumnIndex(MonotributoCategory.KEY_CATEGORY)));
        return monotributoCategory;
    }
}
