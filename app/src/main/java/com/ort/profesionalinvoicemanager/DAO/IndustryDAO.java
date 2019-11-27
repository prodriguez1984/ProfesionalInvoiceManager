package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class IndustryDAO extends AbstractDao {
    private final String TABLE = "INDUSTRY";
    private static IndustryDAO instance;

    private IndustryDAO() {
        super();
    }

    public static IndustryDAO getInstance() {
        if (instance == null) {
            instance = new IndustryDAO();
        }
        return instance;
    }

    @Override
    protected String getTableNameForModel() {
        return TABLE;
    }

    @Override
    protected Industry mapFromCursor(Cursor c) {
        Industry industry = new Industry();
        industry.setOid(c.getString(c.getColumnIndex(Industry.KEY_OID)));
        industry.setName(c.getString(c.getColumnIndex(industry.KEY_NAME)));
        industry.setTelephone(c.getString(c.getColumnIndex(industry.KEY_TELEPHONE)));
        industry.setCellphone(c.getString(c.getColumnIndex(industry.KEY_CELLPHONE)));
        industry.setAddress(c.getString(c.getColumnIndex(industry.KEY_ADDRESS)));
        industry.setTaxInformation(new TaxInformation(c.getString(c.getColumnIndex(industry.KEY_TAX_INFORMATION))));
        try {
            industry.setActivityStart(iso8601Format.parse((c.getString(c.getColumnIndex(industry.KEY_ACTIVITY_START)))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        industry.setMail(c.getString(c.getColumnIndex(industry.KEY_MAIL)));
        return industry;
    }
    public Industry getCompleteIndustryByOid(String oid){
        Industry industry = getByOid(oid);
        industry.setTaxInformation(TaxInformationDAO.getInstance().getCompleteTaxInformationByOid(industry.getTaxInformation().getOid()));
        return industry;
    }
}
