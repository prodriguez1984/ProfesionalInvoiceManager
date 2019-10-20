package com.ort.profesionalinvoicemanager.model.base;

import android.content.Context;

import com.ort.profesionalinvoicemanager.model.user.User;

public class ApplicationContext {

    private SQLiteManager db;
    private User loggedUser;

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
        loggedUser=null;
    }

    public SQLiteManager getDb() {
        return db;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
