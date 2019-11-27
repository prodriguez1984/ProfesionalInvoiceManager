package com.ort.profesionalinvoicemanager.views.ui.invoice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ort.profesionalinvoicemanager.DAO.InvoiceDAO;
import com.ort.profesionalinvoicemanager.DAO.ProductDAO;
import com.ort.profesionalinvoicemanager.model.invoice.Invoice;
import com.ort.profesionalinvoicemanager.model.invoice.InvoiceVO;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.R;

public class InvoiceInspect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_inspect);
        InvoiceVO i= InvoiceDAO.getInstance().getInvoiceForInspect((String)getIntent().getExtras().get("invoiceOid"));
        TextView txt=findViewById(R.id.invoice_inspect_txtInvoiceNumber);
        txt.setText(i.getInvoiceNumber());
        txt=findViewById(R.id.invoice_inspect_txtCustomerName);
        txt.setText(i.getCustomer());
        txt=findViewById(R.id.invoice_inspect_txtCustomerDoc);
        txt.setText(i.getCustomerDocument());
        txt=findViewById(R.id.invoice_inspect_txtCustomerIva);
        txt.setText(i.getIva());
        txt=findViewById(R.id.invoice_inspect_txtCustomerAddres);
        txt.setText(i.getAddress());
        txt=findViewById(R.id.invoice_inspect_txtCustomerMail);
        txt.setText(i.getMail());
        txt=findViewById(R.id.invoice_inspect_txtPaymentCondition);
        txt.setText(i.getPayment_condition());
        txt=findViewById(R.id.invoice_inspect_txtAmount);
        txt.setText(i.getAmount());
        txt=findViewById(R.id.invoice_inspect_txtProduct);
        txt.setText("");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
