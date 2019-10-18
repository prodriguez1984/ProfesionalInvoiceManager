package com.ort.profesionalinvoicemanager.DAO;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;

public class UserDAO extends AbstractDao {

    public void saveUser(User u)throws Exception {
        super.clearObjects();
        super.addObjectToManipulate(u);
        super.addObjectToManipulate(u.getIndustry());
        super.addObjectToManipulate(u.getIndustry().getTaxInformation());
        super.insert();
    }

    public User getUserByName(String name) {
        Cursor c = executeQuery(User.TABLE,
                null,
                User.KEY_USER + " = ?",
                new String[]{name},
                null,
                null,
                null);
        if (c.getCount() == 0) {
            return null;
        }
        User u = new User();
        u.setOid(c.getString(c.getColumnIndex(User.KEY_USER)));
        u.setMail(c.getString(c.getColumnIndex(User.KEY_MAIL)));
        u.setPassword(c.getString(c.getColumnIndex(User.KEY_PASS)));
        u.setUserName(c.getString(c.getColumnIndex(User.KEY_USER)));
        return u;
    }

    public User getUserByMail(String mail) {
        Cursor c = executeQuery(User.TABLE,
                null,
                User.KEY_MAIL + " = ?",
                new String[]{mail},
                null,
                null,
                null);
        if (c.getCount() == 0) {
            return null;
        }
        User u = new User();
        u.setOid(c.getString(c.getColumnIndex(User.KEY_USER)));
        u.setMail(c.getString(c.getColumnIndex(User.KEY_MAIL)));
        u.setPassword(c.getString(c.getColumnIndex(User.KEY_PASS)));
        u.setUserName(c.getString(c.getColumnIndex(User.KEY_USER)));
        return u;
    }




}
