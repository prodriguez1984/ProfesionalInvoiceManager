package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.ApplicationContext;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.base.SQLiteManager;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.invoice.Invoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class StatisticsDAO extends AbstractDao {
    private static StatisticsDAO instance;
    private final String QUERY_TOTAL_YEAR =
            "SELECT * FROM INVOICE " +
            " WHERE substr(INVOICE_DATE,7,4) = strftime('%Y','now')";
    private final String TABLE = "INVOICE";
    private final  String CONDITION = ApplicationContext.getInstance().getLoggedUser().getOid();


    private StatisticsDAO() {
        super();
    }

    public static StatisticsDAO getInstance() {
        if (instance == null) {
            instance = new StatisticsDAO();
        }
        return instance;
    }

    @Override
    protected String getTableNameForModel() {
        return null;
    }

    @Override
    protected <T extends PersistentObject> T mapFromCursor(Cursor c) {
        return null;
    }

    public Integer getTotalMonth(){
        int cant = 0;
//        ArrayList<Invoice> list =  InvoiceDAO.getInstance().getAllWithCondition("");
        ArrayList<Invoice> list =  InvoiceDAO.getInstance().getAll();
        cant =(((int) list.stream().filter(p -> p.getInvoiceDate().getMonth() == Calendar.getInstance().getTime().getMonth() && p.getInvoiceDate().getYear() == Calendar.getInstance().getTime().getYear() ).count()));
        return new Integer(cant);
    }

    public Integer getTotalYear(){
      /*  GregorianCalendar gc = new GregorianCalendar();
        String since=gc.getTime().getYear()+"-01-01";
        String until=gc.getTime().getYear()+"-12-31";
        Cursor c = executeSqlQuery("select sum(AMOUNT) AS total from INVOICE where date(INVOICE_DATE) between date(?) and date(?)",            new String[]{since,until});
        if (c.getCount() == 0) {
            return new Double("0");
        }
        c.moveToNext();
        return new Double(c.getDouble(c.getColumnIndex("total")));*/

        int cant = 0;
//        ArrayList<Invoice> list =  InvoiceDAO.getInstance().getAllWithCondition("");
        ArrayList<Invoice> list =  InvoiceDAO.getInstance().getAll();
        cant =(((int) list.stream().filter(p -> p.getInvoiceDate().getYear() == Calendar.getInstance().getTime().getYear()).count()));
        return new Integer(cant);
    }

    public String getTotalMaxClient(){
        Cursor c = executeSqlQuery("select max(C.NAME||' '||C.LAST_NAME) AS CLIENTE, COUNT(1) AS CANTIDAD from INVOICE i inner join CLIENT C ON I.CLIENT_OID=C.OID GROUP BY C.NAME||' '||C.LAST_NAME",  null);
        if (c.getCount() == 0) {
            return "";
        }
        c.moveToNext();
        return c.getString(c.getColumnIndex("CLIENTE")) + " Total ventas: "+c.getString(c.getColumnIndex("CANTIDAD"));
    }
}
