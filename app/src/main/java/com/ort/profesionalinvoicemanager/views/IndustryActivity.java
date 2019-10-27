package com.ort.profesionalinvoicemanager.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ort.profesionalinvoicemanager.DAO.DocTypeDAO;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;

import java.util.ArrayList;
import java.util.Collections;
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
    private Spinner spinnerDocType;
    private String idDoc, descDoc;
    private List<DocumentType> lstDocumentTypes;

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
//            lstDocumentTypes = DocTypeDAO.getInstance().SelectAll(DocumentType.class, "DOCUMENT_TYPE","" );
            lstDocumentTypes =  DocTypeDAO.getInstance().getAll();
        }catch(Exception e){
            Log.d( "",e.getMessage());
        }

        //================Datos cargados desde SQLite=====================//
        //Instancio la variable helper a PaisesDataBaseHelper.getInstance()
        //Sirve para poder usar los métodos y propiedades SQLite creados anteriormente

        //Implemento el setOnItemSelectedListener: para realizar acciones cuando se seleccionen los ítems
        spinnerDocType.setOnItemSelectedListener(this);
        //Convierto la variable List<> en un ArrayList<>()
        List<String> listaDocs = new ArrayList<>();
        //Almaceno el tamaño de la lista getAllPaises()

        //Agrego los nombres de los países obtenidos y lo almaceno en  `listaPaisesSql`
        for(int i = 0; i < lstDocumentTypes.size(); i++){
            listaDocs.add(lstDocumentTypes.get(i).getDescription());
        }
        //Implemento el adapter con el contexto, layout, listaPaisesSql
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDocs);
        //Cargo el spinner con los datos
        spinnerDocType.setAdapter(adapter);
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
        spinnerDocType = (Spinner) findViewById(R.id.taxDocType);;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.taxDocType:
                //Almaceno el id del país seleccionado
                idDoc = lstDocumentTypes.get(position).getOid();
                //Almaceno el nombre del país seleccionado
                descDoc = lstDocumentTypes.get(position).getDescription();

                Toast.makeText(this, "Id doc: " + idDoc + " - Desc doc: " + descDoc, Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
