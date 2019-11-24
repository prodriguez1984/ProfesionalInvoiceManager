package com.ort.profesionalinvoicemanager.DAO;

import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;

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
        client.setName(c.getString(c.getColumnIndex(Client.KEY_NAME)));
        client.setLastName(c.getString(c.getColumnIndex(Client.KEY_LAST_NAME)));
        client.setMail(c.getString(c.getColumnIndex(Client.KEY_MAIL)));
        client.setAddress(c.getString(c.getColumnIndex(Client.KEY_ADDRESS)));
        client.setTaxInformation(new TaxInformation(c.getString(c.getColumnIndex(Client.KEY_TAX_INFORMATION))));
        return client;
    }

    public void delete(Client client){
        super.addObjectToManipulate(client);
        try {
            super.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client activeClient(Client client){
        client.active=new Integer(1);
        super.addObjectToManipulate(client);
        try {
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }


    public Client getCompleteClientByOid(String oid) {
        Client client=getByOid(oid);
        client.setTaxInformation(TaxInformationDAO.getInstance().getCompleteTaxInformationByOid(client.getTaxInformation().getOid()));
        return client;
    }

    public void edit(Client client){
        super.addObjectToManipulate(client);
        try {
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
