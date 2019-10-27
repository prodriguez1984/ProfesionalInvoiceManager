package com.ort.profesionalinvoicemanager.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class TaxActivity extends AppCompatActivity {
    private Spinner taxDocType;
    private Spinner taxIVACat;
    private Spinner spinnerTaxMonoCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tax_information);
        configView();


    }

    private void configView() {
        taxDocType = (Spinner) findViewById(R.id.taxDocType);
        taxIVACat = (Spinner) findViewById(R.id.taxIVACat);
        spinnerTaxMonoCat = (Spinner) findViewById(R.id.spinnerTaxMonoCat);

        List<String> spinnerTaxArray =  new ArrayList<String>();
        spinnerTaxArray.add("item1");
        spinnerTaxArray.add("item2");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerTaxArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taxDocType.setAdapter(adapter);
    }
}
