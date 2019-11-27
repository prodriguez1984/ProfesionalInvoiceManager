package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;
import android.widget.TabHost;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.invoice.Invoice;
import com.ort.profesionalinvoicemanager.model.invoice.PaymentCondition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        invoice.setCAE(Integer.parseInt(c.getString(c.getColumnIndex(invoice.KEY_CAE))));
        invoice.setPaymentCondition(new PaymentCondition(c.getString(c.getColumnIndex(Invoice.KEY_PAYMENT_CONDITION))));
        invoice.setClient(new Client(c.getString(c.getColumnIndex(Invoice.KEY_CLIENT))));
        try {
            invoice.setInvoiceSince(iso8601Format.parse(c.getString(c.getColumnIndex(invoice.KEY_SINCE))));
            invoice.setInvoiceUntil(iso8601Format.parse((c.getString(c.getColumnIndex(invoice.KEY_UNTIL)))));
            invoice.setDueDate(iso8601Format.parse((c.getString(c.getColumnIndex(invoice.KEY_DUE_DATE)))));
            invoice.setInvoiceDate(iso8601Format.parse((c.getString(c.getColumnIndex(invoice.KEY_INVOICE_DATE)))));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        invoice.setIndustry(new Industry(c.getString(c.getColumnIndex(Invoice.KEY_INDUSTRY))));
        invoice.setAmount(Double.parseDouble(c.getString(c.getColumnIndex(invoice.KEY_AMOUNT))));
        invoice.setDiscountAmount(Double.parseDouble(c.getString(c.getColumnIndex(invoice.KEY_DISCOUNT_AMOUNT))));
        invoice.setDiscountRate(Double.parseDouble(c.getString(c.getColumnIndex(invoice.KEY_DISCOUNT_RATE))));
        invoice.setNetAmount(Double.parseDouble(c.getString(c.getColumnIndex(invoice.KEY_NET_AMOUNT))));


//        invoice.setOid(c.getString(c.getColumnIndex(ivaCategory.KEY_OID)));
//        invoice.setDescription(c.getString(c.getColumnIndex(ivaCategory.KEY_DESCRIPTION)));
        return invoice;
    }


    public Invoice getCompleteInvoiceByOid(String oid){
        Invoice invoice =(Invoice) getAll().get(0);
        invoice.setPaymentCondition(PaymentConditionDAO.getInstance().getByOid(invoice.getPaymentCondition().getOid()));
        invoice.setIndustry(IndustryDAO.getInstance().getCompleteIndustryByOid(invoice.getIndustry().getOid()));
        invoice.setClient(ClientDAO.getInstance().getByOid(invoice.getClient().getOid()));
        return invoice;
    }
}
