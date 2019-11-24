package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.invoice.PaymentCondition;

public class PaymentConditionDAO extends AbstractDao {
    private final String TABLE = "PAYMENT_CONDITION";
    private static PaymentConditionDAO instance;

    private PaymentConditionDAO() {
        super();
    }

    public static PaymentConditionDAO getInstance() {
        if (instance == null) {
            instance = new PaymentConditionDAO();
        }
        return instance;
    }

    @Override
    protected String getTableNameForModel() {
        return TABLE;
    }

    @Override
    protected PaymentCondition mapFromCursor(Cursor c) {
        PaymentCondition paymentCondition = new PaymentCondition();
        paymentCondition.setOid(c.getString(c.getColumnIndex(PaymentCondition.KEY_OID)));
        paymentCondition.setCode(Integer.parseInt(c.getString(c.getColumnIndex(PaymentCondition.KEY_CODE))));
        paymentCondition.setDescription(c.getString(c.getColumnIndex(PaymentCondition.KEY_DESCRIPTION)));
        return paymentCondition;
    }
}
