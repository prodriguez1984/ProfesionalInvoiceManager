package com.ort.profesionalinvoicemanager.model.base;

public abstract class AbstractDao {

    private String doInsert(PersistentObject o){
        return " ";
    }

    private String doDelete(PersistentObject o){
        return "DELETE FROM "+o.getTableName()+" WHERE OID ="+o.getOid();
    }

    private String doUpdate(PersistentObject o){
        return"";
    }





}
