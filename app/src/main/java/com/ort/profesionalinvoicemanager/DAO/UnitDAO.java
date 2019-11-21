package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.product.Unit;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;

public class UnitDAO extends AbstractDao {
    private static UnitDAO instance;

    private UnitDAO() {
        super();
    }

    public static UnitDAO getInstance() {
        if (instance == null) {
            instance = new UnitDAO();
        }
        return instance;
    }

    @Override
    protected String getTableNameForModel() {
        return Unit.TABLE;
    }

    @Override
    protected Unit mapFromCursor(Cursor c) {
        Unit unit = new Unit();
        unit.setCode(Integer.parseInt(c.getString(c.getColumnIndex(DocumentType.KEY_CODE))));
        unit.setOid(c.getString(c.getColumnIndex(DocumentType.KEY_OID)));
        unit.setDescription(c.getString(c.getColumnIndex(DocumentType.KEY_DESCRIPTION)));
        return unit;
    }

}
