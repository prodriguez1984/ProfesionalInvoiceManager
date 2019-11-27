package com.ort.profesionalinvoicemanager.views.ui.invoice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ort.profesionalinvoicemanager.DAO.BillingDAO;
import com.ort.profesionalinvoicemanager.DAO.ClientDAO;
import com.ort.profesionalinvoicemanager.DAO.EmailHelper;
import com.ort.profesionalinvoicemanager.DAO.InvoiceDAO;
import com.ort.profesionalinvoicemanager.DAO.PaymentConditionDAO;
import com.ort.profesionalinvoicemanager.DAO.ProductDAO;
import com.ort.profesionalinvoicemanager.DAO.UnitDAO;
import com.ort.profesionalinvoicemanager.model.base.ApplicationContext;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.invoice.Invoice;
import com.ort.profesionalinvoicemanager.model.invoice.InvoiceDetail;
import com.ort.profesionalinvoicemanager.model.invoice.PaymentCondition;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.model.product.Unit;
import com.ort.profesionalinvoicemanager.views.R;
import com.ort.profesionalinvoicemanager.views.Utils.PdfHelper;
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CreateInvoice extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerProduct;
    private String oidProduct, descProduct;
    private List<Product> lstProducts;

    private Spinner spinnerClient;
    private String oidClient, descClient;
    private List<Client> lstClient;

    private Spinner spinnerPaymentCondition;
    private String oidPaymentCondition, descPaymentCondition;
    private List<PaymentCondition> lstPaymentCondition;

    public Boolean getSaveAction() {
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);
        initSpinner(null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        FloatingActionButton fab = findViewById(R.id.billing_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        TextInputEditText txt;
        position=position-1;
        if(position >= 0){
            //Doc type
            switch (adapterView.getId()) {
                case R.id.invoice_create_spinner_product:
                    oidProduct = lstProducts.get(position).getOid();
                    descProduct = lstProducts.get(position).getCode()+" - "+lstProducts.get(position).getName();
                    txt=findViewById(R.id.invoice_create_price);
                    txt.setText(lstProducts.get(position).getPrice().toString());
                    break;
            }
            //Category
            switch (adapterView.getId()) {
                case R.id.invoice_create_spinnerClient:
                    oidClient = lstClient.get(position).getOid();
                    descClient = lstClient.get(position).getName()+" "+lstClient.get(position).getLastName();

                    Client c=ClientDAO.getInstance().getCompleteClientByOid(oidClient);
                    txt=findViewById(R.id.invoice_create_txtCustomerDoc);
                    txt.setText(c.getTaxInformation().getDocumentNumber());

                    txt=findViewById(R.id.invoice_create_txtCustomerIva);
                    txt.setText(c.getTaxInformation().getIva().getDescription());

                    txt=findViewById(R.id.invoice_create_txtCustomerAddres);
                    txt.setText(c.getAddress());

                    txt=findViewById(R.id.invoice_create_txtCustomerMail);
                    txt.setText(c.getMail());
                    break;
            }
            //Iva
            switch (adapterView.getId()) {
                case R.id.invoice_create_spinner_payment_condition:
                    oidPaymentCondition = lstPaymentCondition.get(position).getOid();
                    descPaymentCondition = lstPaymentCondition.get(position).getDescription();
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void initSpinner(String initialUnit) {
        //products
        this.spinnerProduct = (Spinner) findViewById(R.id.invoice_create_spinner_product);
        try {
            lstProducts = ProductDAO.getInstance().getAllWithActiveCondition(true);
        } catch (Exception e) {
            Log.d("", e.getMessage());
        }

        spinnerProduct.setOnItemSelectedListener(this);
        List<String> lst = new ArrayList<>();
        lst.add(StringConstant.DEFAULT_DOCTYPE_SPINNER);
        for (int i = 0; i < lstProducts.size(); i++) {
            lst.add(lstProducts.get(i).getCode()+" - "+lstProducts.get(i).getName());
        }
        ArrayAdapter<String> adapterDocType = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lst);
        spinnerProduct.setAdapter(adapterDocType);
        if (initialUnit != null) {
            spinnerProduct.setSelection(adapterDocType.getPosition(initialUnit));
        }

        //client
        this.spinnerClient = (Spinner) findViewById(R.id.invoice_create_spinnerClient);
        try {
            lstClient = ClientDAO.getInstance().getAllWithActiveCondition(true);
        } catch (Exception e) {
            Log.d("", e.getMessage());
        }

        spinnerClient.setOnItemSelectedListener(this);
        lst = new ArrayList<>();
        lst.add(StringConstant.DEFAULT_DOCTYPE_SPINNER);
        for (int i = 0; i < lstProducts.size(); i++) {
            lst.add(lstClient.get(i).getName()+" "+lstClient.get(i).getLastName());
        }
        adapterDocType = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lst);
        spinnerClient.setAdapter(adapterDocType);
        if (initialUnit != null) {
            spinnerClient.setSelection(adapterDocType.getPosition(initialUnit));
        }

        //payment condition
        this.spinnerPaymentCondition = (Spinner) findViewById(R.id.invoice_create_spinner_payment_condition);
        try {
            lstPaymentCondition = PaymentConditionDAO.getInstance().getAll();
        } catch (Exception e) {
            Log.d("", e.getMessage());
        }

        spinnerPaymentCondition.setOnItemSelectedListener(this);
        lst = new ArrayList<>();
        lst.add(StringConstant.DEFAULT_DOCTYPE_SPINNER);
        for (int i = 0; i < lstProducts.size(); i++) {
            lst.add(lstPaymentCondition.get(i).getDescription());
        }
        adapterDocType = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lst);
        spinnerPaymentCondition.setAdapter(adapterDocType);
        if (initialUnit != null) {
            spinnerPaymentCondition.setSelection(adapterDocType.getPosition(initialUnit));
        }
    }

    private void save(){
        TextInputEditText txt=findViewById(R.id.invoice_create_quantity);
        if (oidClient==null||oidPaymentCondition==null||oidProduct==null||txt.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Complete los datos", Toast.LENGTH_SHORT).show();

        }else {
            GregorianCalendar gc = new GregorianCalendar();
            Date date = gc.getTime();
            Invoice i = new Invoice();
            i.setIndustry(ApplicationContext.getInstance().getLoggedUser().getIndustry());
            i.setClient(new Client(oidClient));
            i.setPaymentCondition(new PaymentCondition(oidPaymentCondition));
            i.setInvoiceDate(date);
            i.setInvoiceSince(date);
            i.setInvoiceUntil(date);
            i.setDueDate(date);
            i.setLetter("A");
            i.setCAE(new Integer("1"));
            i.setDiscountAmount(new Double("0"));
            i.setDiscountRate(new Double("0"));
            Product p = ProductDAO.getInstance().getByOid(oidProduct);
            InvoiceDetail det = new InvoiceDetail();
            det.setQuantity(new Integer(txt.getText().toString()));
            det.setAmount(p.getPrice() * det.getQuantity());
            det.setDiscountAmount(new Double("0"));
            det.setDiscountRate(new Double("0"));
            det.setProduct(p);
            det.setNetAmount(p.getPrice());
            i.setAmount(det.getAmount());
            i.setNetAmount(det.getAmount());

            InvoiceDAO.getInstance().saveInvoice(i);

        PdfHelper pdfHelper = new PdfHelper();
        Invoice invoice= InvoiceDAO.getInstance().getCompleteInvoiceByOid(i.getOid());
        String pdf =  pdfHelper.getPdf(invoice);
        BillingDAO billingDAO = new BillingDAO();
        billingDAO.SendEmail(pdf,this);
            finish();
        }
    }

}
