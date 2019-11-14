package com.ort.profesionalinvoicemanager.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ort.profesionalinvoicemanager.DAO.DocTypeDAO;
import com.ort.profesionalinvoicemanager.DAO.IvaCategoryDAO;
import com.ort.profesionalinvoicemanager.DAO.MonotributoCategoryDAO;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;

import java.util.ArrayList;
import java.util.List;

public class IndustryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView txtIndustryCreate;
    private EditText etCreateIndustry;
    private EditText etAddressCreateIndustry;
    private EditText etEmailCreateIndustry;
    private EditText etPhoneCreateIndustry;
    private EditText etCellPhoneCreateIndustry;
    private EditText etdatestartCreateIndustry;
    private TextView tvIndustryWelcome;

    private Spinner spinnerTaxMonoCat;
    private List<MonotributoCategory> lstTaxMonoCat;
    private String idMonoCat, descMonoCat;

    private Spinner spinnerDocType;
    private String idDoc, descDoc;
    private List<DocumentType> lstDocumentTypes;

    private Spinner spinnerIvaCategory;
    private String idIva, descIva;
    private List<IvaCategory> lstIvaCategory;

    //Spinner tipo dni

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configView();
//
        String value;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            value = bundle.getString("email");
            tvIndustryWelcome.setText("Bienvenido: " + value);
        }

        configView();
        initSpinner();
    }

    private void initSpinner() {
        try {
            lstDocumentTypes =  DocTypeDAO.getInstance().getAll();
            lstTaxMonoCat = MonotributoCategoryDAO.getInstance().getAll();
            lstIvaCategory = IvaCategoryDAO.getInstance().getAll();

        }catch(Exception e){
            Log.d( "",e.getMessage());
        }

        //Implemento el setOnItemSelectedListener: para realizar acciones cuando se seleccionen los ítems
        spinnerDocType.setOnItemSelectedListener(this);
        spinnerTaxMonoCat.setOnItemSelectedListener(this);
        //Convierto la variable List<> en un ArrayList<>()
        List<String> listaDocs = new ArrayList<>();
        List<String> listaCats = new ArrayList<>();
        List<String> listaIva = new ArrayList<>();
        //Almaceno el tamaño de la lista getAllPaises()

        //Agrego los nombres de los países obtenidos y lo almaceno en  `listaPaisesSql`
        listaDocs.add(StringConstant.DEFAULT_DOCTYPE_SPINNER);
        for(int i = 0; i < lstDocumentTypes.size(); i++){
            listaDocs.add(lstDocumentTypes.get(i).getDescription());
        }
        listaCats.add(StringConstant.DEFAULT_MONOCAT_SPINNER);
        for(int i = 0; i < lstTaxMonoCat.size(); i++){
            listaCats.add(lstTaxMonoCat.get(i).getCategory());
        }
        listaIva.add(StringConstant.DEFAULT_IVACAT_SPINNER);
        for(int i = 0; i < lstIvaCategory.size(); i++){
            listaIva.add(lstIvaCategory.get(i).getDescription());
        }
        //Implemento el adapter con el contexto, layout, listaPaisesSql
        ArrayAdapter<String> adapterDocType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDocs);
        ArrayAdapter<String> adapterMonoCat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCats);
        ArrayAdapter<String> adapterIvaCat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaIva);

        //Cargo el spinner con los datos
        spinnerDocType.setAdapter(adapterDocType);
        spinnerTaxMonoCat.setAdapter(adapterMonoCat);
        spinnerIvaCategory.setAdapter(adapterIvaCat);
    }

    private void configView() {
//        tvIndustryWelcome = (TextView) findViewById(R.id.tvIndustryWelcome);
        txtIndustryCreate = (TextView) findViewById(R.id.txtIndustryCreate);
        etCreateIndustry = (EditText) findViewById(R.id.etCreateIndustry);
        etAddressCreateIndustry = (EditText) findViewById(R.id.etAddressCreateIndustry);
        etEmailCreateIndustry = (EditText) findViewById(R.id.etEmailCreateIndustry);
        etPhoneCreateIndustry = (EditText) findViewById(R.id.etPhoneCreateIndustry);
        etCellPhoneCreateIndustry = (EditText) findViewById(R.id.etCellPhoneCreateIndustry);
        etdatestartCreateIndustry = (EditText) findViewById(R.id.etdatestartCreateIndustry);

        spinnerDocType = (Spinner) findViewById(R.id.spinnerTaxDocType);
        spinnerTaxMonoCat =  (Spinner) findViewById(R.id.spinnerTaxMonoCat);
        spinnerIvaCategory =  (Spinner) findViewById(R.id.spinnerTaxIVACat);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(position > 0){
            //Doc type
            switch (adapterView.getId()) {
                case R.id.spinnerTaxDocType:
                    //Almaceno el id del país seleccionado
                    idDoc = lstDocumentTypes.get(position).getOid();
                    //Almaceno el nombre del país seleccionado
                    descDoc = lstDocumentTypes.get(position).getDescription();

                    Toast.makeText(this, "Id doc: " + idDoc + " - Desc doc: " + descDoc, Toast.LENGTH_SHORT).show();

                    break;
            }
            //Category
            switch (adapterView.getId()) {
                case R.id.spinnerTaxMonoCat:
                    //Almaceno el id del país seleccionado
                    idMonoCat = lstTaxMonoCat.get(position).getOid();
                    //Almaceno el nombre del país seleccionado
                    descMonoCat = lstTaxMonoCat.get(position).getCategory();

                    Toast.makeText(this, "Id doc: " + idMonoCat + " - Desc doc: " + descMonoCat, Toast.LENGTH_SHORT).show();

                    break;
            }
            //Iva
            switch (adapterView.getId()) {
                case R.id.spinnerTaxIVACat:
                    //Almaceno el id del país seleccionado
                    idIva = lstIvaCategory.get(position).getOid();
                    //Almaceno el nombre del país seleccionado
                    descIva = lstIvaCategory.get(position).getDescription();

                    Toast.makeText(this, "Id iva: " + idIva + " - Desc iva: " + descIva, Toast.LENGTH_SHORT).show();

                    break;
            }
            // get spinner value
        }else{
            Toast.makeText(this,"Seleccione un valor", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
