package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.user.User;

import java.util.ArrayList;

public class UserDAO extends AbstractDao {
    private static UserDAO instance;

    private UserDAO() {
        super();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public void saveUser(User u) throws Exception {
        super.clearObjects();
        super.addObjectToManipulate(u);
        super.addObjectToManipulate(u.getIndustry());
        super.addObjectToManipulate(u.getIndustry().getTaxInformation());
        super.insert();
    }

    public User getUserByName(String name) {
        Cursor c = executeSqlQuery("Select * from USER where USER_NAME = ? and ACTIVE=1", new String[]{name});
        if (c.getCount() == 0) {
            return null;
        }
        return mapFromCursor(c);
    }

    public User getUserByMail(String mail) {
        Cursor c = executeSqlQuery("Select * from "+User.TABLE+" where MAIL = ? and ACTIVE=1", new String[]{mail});
        if (c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        return mapFromCursor(c);
    }

    @Override
    protected String getTableNameForModel() {
        return User.TABLE;
    }

    @Override
    protected User mapFromCursor(Cursor c) {
        User u = new User();
        u.setOid(c.getString(c.getColumnIndex(User.KEY_OID)));
        u.setMail(c.getString(c.getColumnIndex(User.KEY_MAIL)));
        u.setPassword(c.getString(c.getColumnIndex(User.KEY_PASS)));
        u.setUserName(c.getString(c.getColumnIndex(User.KEY_USER)));
        u.setIndustry(new Industry(c.getString(c.getColumnIndex(User.KEY_INDUSTRY))));
        return u;
    }
}
