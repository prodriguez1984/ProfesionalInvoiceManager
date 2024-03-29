package com.ort.profesionalinvoicemanager.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.base.PersistentObject;
import com.ort.profesionalinvoicemanager.model.base.PersistentObjectWithLogicalDeletion;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.model.product.Unit;

public class ProductDAO extends AbstractDao {

    private static ProductDAO instance;

    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    @Override
    protected String getTableNameForModel() {
        return Product.TABLE;
    }

    @Override
    protected Product mapFromCursor(Cursor c) {
        Product p =new Product();
        p.setName(c.getString(c.getColumnIndex(Product.KEY_NAME)));
        p.setDescription(c.getString(c.getColumnIndex(Product.KEY_DESCRIPTION)));
        p.setCode(c.getString(c.getColumnIndex(Product.KEY_CODE)));
        p.setPrice(c.getDouble(c.getColumnIndex(Product.KEY_PRICE)));
        p.setProductType(c.getString(c.getColumnIndex(Product.KEY_PRODUCT_TYPE)));
        p.setUnit(new Unit(c.getString(c.getColumnIndex(Product.KEY_UNIT_OID))));
        return p;
    }

    public void deleteProduct(Product p){
        super.addObjectToManipulate(p);
        try {
            super.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product getCompleteProductByOid(String oid){
        Product p=getByOid(oid);
        p.setUnit(UnitDAO.getInstance().getByOid(p.getUnit().getOid()));
        return p;
    }

    public Product activeProduct(Product p){
        p.active=new Integer(1);
        super.addObjectToManipulate(p);
        try {
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public void updateProduct(Product p) throws Exception {
        addObjectToManipulate(p);
        update();
    }

    public void createProduct(Product p) throws Exception {
        addObjectToManipulate(p);
        insert();
    }
}
