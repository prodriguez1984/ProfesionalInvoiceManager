package com.ort.profesionalinvoicemanager.views.ui.products;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.ort.profesionalinvoicemanager.DAO.ProductDAO;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.R;

public class ProductInspection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inspection);
        Product p= ProductDAO.getInstance().getCompleteProductByOid((String)getIntent().getExtras().get("productOid"));
        TextView txt=findViewById(R.id.product_txtCode);
        txt.setText(p.getCode());
        txt=findViewById(R.id.product_txtName);
        txt.setText(p.getName());
        txt=findViewById(R.id.product_txtDesc);
        txt.setText(p.getDescription());
        txt=findViewById(R.id.product_txtPrice);
        txt.setText(p.getPrice().toString());
        txt=findViewById(R.id.product_txtUnit);
        txt.setText(p.getUnit().getDescription());
        Switch s=findViewById(R.id.product_active);
        s.setChecked(p.isActive());
        ImageView icon=findViewById(R.id.product_inspect_icon);
        txt=findViewById(R.id.product_inspect_icon_text);
        if (Product.IDENTIFICATOR_PRODUCT.equals(p.getProductType())) {
            icon.setImageResource(R.drawable.ic_product);
            txt.setText(R.string.product_key);
        } else {
            icon.setImageResource(R.drawable.ic_service);
            txt.setText(R.string.service_key);
        }
    }
}
