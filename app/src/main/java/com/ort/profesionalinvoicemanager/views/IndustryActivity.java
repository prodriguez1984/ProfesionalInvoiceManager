package com.ort.profesionalinvoicemanager.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.DAO.DocTypeDAO;
import com.ort.profesionalinvoicemanager.DAO.IvaCategoryDAO;
import com.ort.profesionalinvoicemanager.DAO.MonotributoCategoryDAO;
import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.industry.Industry;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;
import com.ort.profesionalinvoicemanager.model.user.User;
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;
import com.ort.profesionalinvoicemanager.views.Utils.ValidateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndustryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String EXTRA_INDUSTRY = "EXTRA_INSUTRY";

    private TextView txtIndustryCreate;
    private EditText etCreateIndustry;
    private EditText etAddressCreateIndustry;
    private EditText etEmailCreateIndustry;
    private EditText etPhoneCreateIndustry;
    private EditText etCellPhoneCreateIndustry;
    private EditText etdatestartCreateIndustry;
    private EditText etTaxDoc;
    private EditText etIibb;
    private TextView tvIndustryWelcome;
    private Button btnRegisterIndustry;

    private TextInputLayout tiloIndustryName;
    private TextInputLayout tiloAddress;
    private TextInputLayout tiloEmail;
    private TextInputLayout tiloPhone;
    private TextInputLayout tiloCellPhone;
    private TextInputLayout tiloStartDate;
    private TextInputLayout tiloDocument;
    private TextInputLayout tiloIibb;

    private Spinner spinnerTaxMonoCat;
    private List<MonotributoCategory> lstTaxMonoCat;
    private String idMonoCat, descMonoCat;

    private Spinner spinnerDocType;
    private String idDoc, descDoc;
    private List<DocumentType> lstDocumentTypes;

    private Spinner spinnerIvaCategory;
    private String idIva, descIva;
    private List<IvaCategory> lstIvaCategory;

    private ArrayAdapter<String> adapterDocType;
    private ArrayAdapter<String> adapterIvaCat;
    private ArrayAdapter<String> adapterMonoCat;

    private String username;
    private String email;
    private String password;

    private Industry mIndustry;
    //Spinner tipo dni

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_industry);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String value;
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = intent.getStringExtra("username");
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
            this.mIndustry = (Industry)intent.getSerializableExtra(EXTRA_INDUSTRY);
        }
        configView();

        initSpinner();
        tvIndustryWelcome.setText("Bienvenido " + username + "\n" +StringConstant.EXPLAIN_INDUSTRY);
        btnRegisterIndustry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String industryName = etCreateIndustry.getText().toString();
                String address = etAddressCreateIndustry.getText().toString();
                String emailService = etEmailCreateIndustry.getText().toString();
                String phoneService = etPhoneCreateIndustry.getText().toString();
                String cellPhone = etCellPhoneCreateIndustry.getText().toString();
                String dateStart = etdatestartCreateIndustry.getText().toString();
                String iibb = etIibb.getText().toString();
                String docType = idDoc;
                String docNumber = etTaxDoc.getText().toString();
                String taxIvaCat = idIva;
                String monoCat = idMonoCat;
                if (validateFields(industryName,address,emailService,phoneService,cellPhone,dateStart,docType,docNumber,taxIvaCat,monoCat,iibb)){
                    User user = new User();
                    user.setUserName(username);
                    user.setMail(email);
                    user.setPassword(password);
                    if (user != null){
                        Industry industry = new Industry();
                        industry.setName(industryName);
                        industry.setAddress(address);
                        industry.setMail(email);
                        industry.setTelephone(phoneService);
                        industry.setCellphone(cellPhone);
                        try {
                            SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
                            industry.setActivityStart(originalFormat.parse(dateStart.replaceAll("\\s","")));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        TaxInformation taxInformation = new TaxInformation();
                        DocumentType documentType = new DocumentType();
                        documentType.setOid(docType);
                        taxInformation.setDocumentType(documentType);
                        taxInformation.setDocumentNumber(docNumber);
                        IvaCategory ivaCategory = new IvaCategory();
                        ivaCategory.setOid(idIva);
                        taxInformation.setIva(ivaCategory);
                        MonotributoCategory monotributoCategory = new MonotributoCategory();
                        monotributoCategory.setOid(monoCat);
                        taxInformation.setMonotributoCategory(monotributoCategory);
                        taxInformation.setIibb(iibb);
                        industry.setTaxInformation(taxInformation);

                        user.setIndustry(industry);
                        try {
                            UserDAO.getInstance().saveUser(user);
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);

                        } catch (Exception e) {
                            showError(e.getMessage());
                        }
                    }else{
                        showError(StringConstant.CREATE_ERROR);
                    }
                };
            }
        });
        if(mIndustry != null){
            this.tiloIndustryName.getEditText().setText(mIndustry.getName());
            this.tiloAddress.getEditText().setText(mIndustry.getAddress());
            this.tiloEmail.getEditText().setText(mIndustry.getMail());
            this.tiloPhone.getEditText().setText(mIndustry.getTelephone());
            this.tiloCellPhone.getEditText().setText(mIndustry.getCellphone());
            this.tiloIibb.getEditText().setText(mIndustry.getTaxInformation().getIibb());
            int pos = adapterDocType.getPosition(mIndustry.getTaxInformation().getDocumentType().getDescription());
            this.spinnerDocType.setSelection(pos);
            pos = adapterIvaCat.getPosition(mIndustry.getTaxInformation().getIva().getDescription());
            this.spinnerIvaCategory.setSelection(pos);
            pos = adapterMonoCat.getPosition(mIndustry.getTaxInformation().getMonotributoCategory().getCategory());
            this.spinnerTaxMonoCat.setSelection(pos);
        }
    }
    private boolean validateFields(String industryName, String address, String emailService, String phoneService, String cellPhone, String dateStart, String docType, String docNumber, String taxIvaCat, String monoCat, String iibb) {
        boolean error = false;
        if (ValidateHelper.validateEmptyString(industryName)){
            tiloIndustryName.setError(StringConstant.INDUSTRY_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(address)){
            tiloAddress.setError(StringConstant.ADDRESS_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(emailService)){
            tiloEmail.setError(StringConstant.EMAIL_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(phoneService)){
            tiloPhone.setError(StringConstant.PHONE_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(cellPhone)){
            tiloCellPhone.setError(StringConstant.CELL_PHONE_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(dateStart)){
            tiloStartDate.setError(StringConstant.DATE_START_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(docNumber)){
            tiloDocument.setError(StringConstant.DOCUMENT_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(dateStart)){
            tiloStartDate.setError(StringConstant.DATE_START_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(idIva)){
            showError(StringConstant.IVA_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(idDoc)){
            showError(StringConstant.IVA_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(idMonoCat)){
            showError(StringConstant.IVA_EMPTY);
            error = true;
        }
        if (ValidateHelper.validateEmptyString(iibb)){
            showError(StringConstant.IIBB_EMPTY);
            error = true;
        }
        return !error;
    }

    private void initSpinner() {
        try {
            lstDocumentTypes = DocTypeDAO.getInstance().getAll();
            lstTaxMonoCat = MonotributoCategoryDAO.getInstance().getAll();
            lstIvaCategory = IvaCategoryDAO.getInstance().getAll();

        } catch (Exception e) {
            Log.d("", e.getMessage());
        }

        //Implemento el setOnItemSelectedListener: para realizar acciones cuando se seleccionen los ítems
        spinnerDocType.setOnItemSelectedListener(this);
        spinnerTaxMonoCat.setOnItemSelectedListener(this);
        spinnerIvaCategory.setOnItemSelectedListener(this);
        //Convierto la variable List<> en un ArrayList<>()
        List<String> listaDocs = new ArrayList<>();
        List<String> listaCats = new ArrayList<>();
        List<String> listaIva = new ArrayList<>();
        //Almaceno el tamaño de la lista getAllPaises()

        //Agrego los nombres de los países obtenidos y lo almaceno en  `listaPaisesSql`
        listaDocs.add(StringConstant.DEFAULT_DOCTYPE_SPINNER);
        for (int i = 0; i < lstDocumentTypes.size(); i++) {
            if (lstDocumentTypes.get(i).getDescription().equals(StringConstant.CUIT) ){
                listaDocs.add(lstDocumentTypes.get(i).getDescription());
            }
        }
        listaCats.add(StringConstant.DEFAULT_MONOCAT_SPINNER);
        for (int i = 0; i < lstTaxMonoCat.size(); i++) {
            listaCats.add(lstTaxMonoCat.get(i).getCategory());
        }
        listaIva.add(StringConstant.DEFAULT_IVACAT_SPINNER);
        for (int i = 0; i < lstIvaCategory.size(); i++) {
            if (lstIvaCategory.get(i).getCode() == 12 || lstIvaCategory.get(i).getCode() == 6){
                listaIva.add(lstIvaCategory.get(i).getDescription());
            }
        }
        //Implemento el adapter con el contexto, layout, listaPaisesSql
        this.adapterDocType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaDocs);
        this.adapterMonoCat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaCats);
        this.adapterIvaCat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaIva);

        //Cargo el spinner con los datos
        spinnerDocType.setAdapter(adapterDocType);
        spinnerTaxMonoCat.setAdapter(adapterMonoCat);
        spinnerIvaCategory.setAdapter(adapterIvaCat);
    }

    private void configView() {
        tvIndustryWelcome = (TextView) findViewById(R.id.txtIndustryCreate);
        txtIndustryCreate = (TextView) findViewById(R.id.txtIndustryCreate);
        etCreateIndustry = (EditText) findViewById(R.id.etCreateIndustry);
        etAddressCreateIndustry = (EditText) findViewById(R.id.etAddressCreateIndustry);
        etEmailCreateIndustry = (EditText) findViewById(R.id.etEmailCreateIndustry);
        etPhoneCreateIndustry = (EditText) findViewById(R.id.etPhoneCreateIndustry);
        etCellPhoneCreateIndustry = (EditText) findViewById(R.id.etCellPhoneCreateIndustry);
        etIibb = (EditText) findViewById(R.id.etiibb);

        etdatestartCreateIndustry = (EditText) findViewById(R.id.etdatestartCreateIndustry);
        etdatestartCreateIndustry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        etTaxDoc = (EditText) findViewById(R.id.etTaxDoc);
        spinnerDocType = (Spinner) findViewById(R.id.spinnerTaxDocType);
        spinnerTaxMonoCat = (Spinner) findViewById(R.id.spinnerTaxMonoCat);
        spinnerIvaCategory = (Spinner) findViewById(R.id.spinnerTaxIVACat);
        btnRegisterIndustry = (Button) findViewById(R.id.btnRegisterIndustry);

        tiloIndustryName = (TextInputLayout) findViewById(R.id.tilo_nameIndustry);
        tiloAddress = (TextInputLayout) findViewById(R.id.tilo_addressCreateIndustry);
        tiloEmail = (TextInputLayout) findViewById(R.id.tilo_emailCreateIndustry);
        tiloPhone = (TextInputLayout) findViewById(R.id.tilo_phoneCreateIndustry);
        tiloCellPhone = (TextInputLayout) findViewById(R.id.tilo_cellphoneCreateIndustry);
        tiloStartDate = (TextInputLayout) findViewById(R.id.tilo_datestartCreateIndustry);
        tiloAddress = (TextInputLayout) findViewById(R.id.tilo_addressCreateIndustry);
        tiloDocument = (TextInputLayout) findViewById(R.id.tilo_tax_Document);
        tiloIibb = (TextInputLayout) findViewById(R.id.tilo_iibb);

    }

//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.etdatestartCreateIndustry:
//
//                break;
//        }
//    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etdatestartCreateIndustry.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (position > 0) {
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
        } else {
            Toast.makeText(this, "Seleccione un valor", Toast.LENGTH_SHORT).show();

        }


    }

    private void showError(String messagge) {
        Toast.makeText(this.getApplicationContext(),
                messagge, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
