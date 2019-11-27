package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;

public class FigureDAO extends AbstractDao {

    @Override
    protected String getTableNameForModel() {
        return null;
    }

    @Override
    protected <T extends PersistentObject> T mapFromCursor(Cursor c) {
        return null;
    }
}
