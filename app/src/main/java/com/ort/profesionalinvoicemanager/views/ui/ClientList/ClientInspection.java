package com.ort.profesionalinvoicemanager.views.ui.ClientList;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ort.profesionalinvoicemanager.DAO.ClientDAO;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;
import com.ort.profesionalinvoicemanager.views.R;

public class ClientInspection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_inspection);
        Client client= ClientDAO.getInstance().getCompleteClientByOid((String)getIntent().getExtras().get("clientOid"));

        TextView txt=findViewById(R.id.client_txtName);
        txt.setText(client.getName());
        txt=findViewById(R.id.client_txtLastName);
        txt.setText(client.getLastName());
        txt=findViewById(R.id.client_txtMail);
        txt.setText(client.getName());
        txt=findViewById(R.id.client_txtAdress);
        txt.setText(client.getAddress());

        TaxInformation taxInfo = client.getTaxInformation();
        txt=findViewById(R.id.taxInfo_txtIibb);
        txt.setText(taxInfo.getIibb());
        txt=findViewById(R.id.taxInfo_txtDocType);
        txt.setText(taxInfo.getDocumentType().getDescription());
        txt=findViewById(R.id.taxInfo_txtIibb);
        txt.setText(taxInfo.getIibb());
        txt=findViewById(R.id.taxInfo_txtDocNum);
        txt.setText(taxInfo.getDocumentNumber());
        txt=findViewById(R.id.taxInfo_txtIVA);
        txt.setText(taxInfo.getIva().getDescription());
        txt=findViewById(R.id.taxInfo_txtMonoCat);
        txt.setText(taxInfo.getMonotributoCategory().getCategory());

        Switch s=findViewById(R.id.product_active);
        s.setChecked(client.isActive());

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
