package com.ort.profesionalinvoicemanager.views.ui.products;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ort.profesionalinvoicemanager.DAO.ProductDAO;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.R;

public class ProductInspection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inspection);
        Product p= ProductDAO.getInstance().getCompleteProductByOid((String)getIntent().getExtras().get("productOid"));

    }
}
