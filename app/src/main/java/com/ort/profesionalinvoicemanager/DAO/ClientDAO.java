package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.client.Client;

import java.util.ArrayList;

public class ClientDAO extends AbstractDao {
    private static ClientDAO instance;

    private ClientDAO() {
        super();
    }

    public static ClientDAO getInstance() {
        if (instance == null) {
            instance = new ClientDAO();
        }
        return instance;
    }

    public void saveUser(Client client) throws Exception {
        super.clearObjects();
        super.addObjectToManipulate(client);
        super.addObjectToManipulate(client.getTaxInformation());
        super.insert();
    }

    @Override
    protected String getTableNameForModel() {
        return Client.TABLE;
    }

    @Override
    protected Client mapFromCursor(Cursor c) {
        Client client = new Client();
        client.setOid(c.getString(c.getColumnIndex(Client.KEY_OID)));
        client.setName(c.getString(c.getColumnIndex(Client.KEY_NAME)));
        client.setLastName(c.getString(c.getColumnIndex(Client.KEY_LAST_NAME)));
        client.setAddress(c.getString(c.getColumnIndex(Client.KEY_ADDRESS)));
        return client;
    }


}
