package com.ort.profesionalinvoicemanager.DAO;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.user.User;

public class UserDAO extends AbstractDao {

    public User mockGet(){
        User mockUser = new User();
        mockUser.setUserName("test@test.com");
        mockUser.setPassword("123456");
        return  mockUser;
    }

}
