package com.ort.profesionalinvoicemanager.model.base;

import android.content.Context;

public class ApplicationContext {

    private SQLiteManager db;

    private static ApplicationContext instance;

    public static ApplicationContext getInstance() {
        if (instance==null){
            instance=new ApplicationContext();
        }
        return instance;
    }

    private ApplicationContext(){

    }

    public void init(Context context){
        db=new SQLiteManager(context);
    }

}
