package com.ort.profesionalinvoicemanager.views.ui.ClientList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ort.profesionalinvoicemanager.DAO.ProductDAO;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.R;

class ClientInspection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inspection);
        Product p= ProductDAO.getInstance().getCompleteProductByOid((String)getIntent().getExtras().get("productOid"));

    }
}
