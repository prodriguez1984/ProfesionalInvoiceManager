package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;
import android.widget.TabHost;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.invoice.Invoice;
import com.ort.profesionalinvoicemanager.model.invoice.InvoiceDetail;
import com.ort.profesionalinvoicemanager.model.invoice.InvoiceVO;
import com.ort.profesionalinvoicemanager.model.invoice.PaymentCondition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        invoice.setInvoiceNumber(new Integer(c.getInt(c.getColumnIndex(invoice.KEY_INVOICE_NUMBER))));
        invoice.setCAE(new Integer(c.getInt(c.getColumnIndex(invoice.KEY_CAE))));
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
        invoice.setAmount(c.getDouble(c.getColumnIndex(invoice.KEY_AMOUNT)));
        invoice.setDiscountAmount(c.getDouble(c.getColumnIndex(invoice.KEY_DISCOUNT_AMOUNT)));
        invoice.setDiscountRate(c.getDouble(c.getColumnIndex(invoice.KEY_DISCOUNT_RATE)));
        invoice.setNetAmount(c.getDouble(c.getColumnIndex(invoice.KEY_NET_AMOUNT)));


//        invoice.setOid(c.getString(c.getColumnIndex(ivaCategory.KEY_OID)));
//        invoice.setDescription(c.getString(c.getColumnIndex(ivaCategory.KEY_DESCRIPTION)));
        return invoice;
    }

    public ArrayList getInvoiceForList(String since,String until) {
        StringBuffer query=new StringBuffer();
        query.append("select I.LETTER||'-0000-' ||I.INVOICE_NUMBER AS INVOICE_NUMBER,");
        query.append("C.NAME||' '||C.LAST_NAME AS CUSTOMER,");
        query.append("I.OID AS OID,");
        query.append("' $ '||I.AMOUNT AS AMOUNT  ");
        query.append("FROM INVOICE I INNER JOIN CLIENT C ON I.CLIENT_OID=C.OID ");
        query.append("where date(INVOICE_DATE) between date(?) and date(?)");


        Cursor c = executeSqlQuery(query.toString(),  new String[]{since,until});
        if (c.getCount() == 0) {
            return new ArrayList<>();
        }
        ArrayList result = new ArrayList<>();
        while (c.moveToNext()) {
            InvoiceVO vo=new InvoiceVO();
            vo.setCustomer(c.getString(c.getColumnIndex("CUSTOMER")));
            vo.setInvoiceNumber(c.getString(c.getColumnIndex("INVOICE_NUMBER")));
            vo.setAmount(c.getString(c.getColumnIndex("AMOUNT")));
            vo.setOid(c.getString(c.getColumnIndex("OID")));

            result.add(vo);
        }
        return result;
    }

    public Invoice getCompleteInvoiceByOid(String oid){
        Invoice invoice =(Invoice) getAll().get(0);
        invoice.setPaymentCondition(PaymentConditionDAO.getInstance().getByOid(invoice.getPaymentCondition().getOid()));
        invoice.setIndustry(IndustryDAO.getInstance().getCompleteIndustryByOid(invoice.getIndustry().getOid()));
        invoice.setClient(ClientDAO.getInstance().getByOid(invoice.getClient().getOid()));
        return invoice;
    }

    public ArrayList getAllWithCondition(String condition) {
        String query = "Select * from " + getTableNameForModel() + " WHERE CLIENT_OID = " + "'" + condition + "'";
        Cursor c = executeSqlQuery(query,null);
        if (c.getCount() == 0) {
            return new ArrayList<>();
        }
        ArrayList result = new ArrayList<>();
        while (c.moveToNext()) {
            result.add(mapBasicData(mapFromCursor(c), c));
        }
        return result;
    }

    private Integer getNextNumber() {
        Cursor c = executeSqlQuery("select max(invoice_number) as NUMBER from INVOICE",null);
        if (c.getCount() == 0) {
            return new Integer("0");
        }
        c.moveToNext();
        return c.getInt(c.getColumnIndex("NUMBER"));
    }



    public InvoiceVO getInvoiceForInspect(String invoiceOid) {
        StringBuffer query = new StringBuffer();
        query.append("SELECT I.LETTER||'-0000-' ||I.INVOICE_NUMBER AS INVOICE_NUMBER,");
        query.append(" C.NAME||' '||C.LAST_NAME AS CUSTOMER,");
        query.append(" DT.DESCRIPTION ||' - '|| T.DOCUMENT_NUMBER AS DOCUMENT_NUMBER,");
        query.append(" IV.DESCRIPTION AS IVA,");
        query.append("I.OID AS OID,");
        query.append(" C.ADDRESS AS ADDRESS,");
        query.append(" C.MAIL AS MAIL,");
        query.append(" PC.DESCRIPTION AS PAYMENT_CONDITION,");
        query.append(" ' $ '||I.AMOUNT AS AMOUNT");
        query.append(" FROM INVOICE I INNER JOIN CLIENT C ON I.CLIENT_OID=C.OID");
        query.append(" INNER JOIN PAYMENT_CONDITION PC ON I.PAYMENT_CONDITION_OID=PC.OID");
        query.append(" INNER JOIN TAX_INFORMATION T ON C.TAX_INFORMATION_OID = T.OID");
        query.append(" INNER JOIN IVA_CATEGORY IV ON T.IVA=IV.OID");
        query.append(" INNER JOIN DOCUMENT_TYPE DT ON DT.OID=T.DOCUMENT_TYPE");
        query.append(" WHERE I.OID = ?");

        Cursor c = executeSqlQuery(query.toString(), new String[]{invoiceOid});
        if (c.getCount() == 0) {
            return null;
        }
        c.moveToNext();
        InvoiceVO vo=new InvoiceVO();
        vo.setAddress(c.getString(c.getColumnIndex("ADDRESS")));
        vo.setAmount(c.getString(c.getColumnIndex("AMOUNT")));
        vo.setCustomer(c.getString(c.getColumnIndex("CUSTOMER")));
        vo.setCustomerDocument(c.getString(c.getColumnIndex("DOCUMENT_NUMBER")));
        vo.setInvoiceNumber(c.getString(c.getColumnIndex("INVOICE_NUMBER")));
        vo.setIva(c.getString(c.getColumnIndex("IVA")));
        vo.setMail(c.getString(c.getColumnIndex("MAIL")));
        vo.setPayment_condition(c.getString(c.getColumnIndex("PAYMENT_CONDITION")));
        vo.setOid(c.getString(c.getColumnIndex("OID")));
        return vo;
    }

    public void saveInvoice(Invoice i){
        i.setInvoiceNumber(getNextNumber());
        addObjectToManipulate(i);
        try {
            insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
