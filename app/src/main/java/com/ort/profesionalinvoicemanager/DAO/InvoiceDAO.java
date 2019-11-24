package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;
import android.widget.TabHost;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.invoice.Invoice;

public class InvoiceDAO extends AbstractDao {
    private static String TABLE = "INVOICE";
    private static InvoiceDAO instance;

    private InvoiceDAO() {
        super();
    }

    public static InvoiceDAO getInstance() {
        if (instance == null) {
            instance = new InvoiceDAO();
        }
        return instance;
    }

    protected String getTableNameForModel() {
        return TABLE;
    }

    protected Invoice mapFromCursor(Cursor c) {
        Invoice invoice = new Invoice();
        invoice.setLetter(c.getString(c.getColumnIndex(invoice.KEY_LETTER)));
        invoice.setInvoiceNumber(Integer.parseInt(c.getString(c.getColumnIndex(invoice.KEY_INVOICE_NUMBER))));
//        invoice.setOid(c.getString(c.getColumnIndex(ivaCategory.KEY_OID)));
//        invoice.setDescription(c.getString(c.getColumnIndex(ivaCategory.KEY_DESCRIPTION)));
        return invoice;
    }

    public Invoice getCompleteInvoiceByOid(String oid){
        Invoice invoice = getByOid(oid);

        return invoice;
    }
}
