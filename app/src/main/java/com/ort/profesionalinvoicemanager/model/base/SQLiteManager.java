package com.ort.profesionalinvoicemanager.model.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;
import com.ort.profesionalinvoicemanager.model.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pim.db";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            for (String classes:PersistenceAndMockData.getPersistenClasses()) {
                PersistentObject object = (PersistentObject) Class.forName(classes).newInstance();
                sqLiteDatabase.execSQL(object.getCreationScript());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createinitialData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void createinitialData(SQLiteDatabase db) {
        for(PersistentObject p:PersistenceAndMockData.getMoObjects()) {
            db.insert(p.getTableName(), null, p.toContentValues());
        }
    }
}
