package com.ort.profesionalinvoicemanager.views.ui.products;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.DAO.DocTypeDAO;
import com.ort.profesionalinvoicemanager.DAO.IvaCategoryDAO;
import com.ort.profesionalinvoicemanager.DAO.MonotributoCategoryDAO;
import com.ort.profesionalinvoicemanager.DAO.ProductDAO;
import com.ort.profesionalinvoicemanager.DAO.UnitDAO;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.model.product.Unit;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;
import com.ort.profesionalinvoicemanager.views.R;
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;
import com.ort.profesionalinvoicemanager.views.Utils.ValidateHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductCreate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerDocType;
    private String oidUnit, descUnit;
    private List<Unit> lstUnits;
    private Product product;
    private Boolean saveAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_create);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String productOid = (String) getIntent().getExtras().get("productOid");
        saveAction = (Boolean) getIntent().getExtras().get("saveAction");
        if (productOid == null) {
            product = new Product();
            product.setPrice(new Double("0"));
            product.setUnit(new Unit());
        } else {
            product = ProductDAO.getInstance().getCompleteProductByOid(productOid);
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (position > 0) {
            switch (adapterView.getId()) {
                case R.id.product_create_unit:
                    oidUnit = lstUnits.get(position-1).getOid();
                    descUnit = lstUnits.get(position-1).getDescription();
                    break;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Seleccione un valor", Toast.LENGTH_SHORT).show();
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void initSpinner(String initialUnit) {
        this.spinnerDocType = (Spinner) findViewById(R.id.product_create_unit);
        try {
            lstUnits = UnitDAO.getInstance().getAll();
        } catch (Exception e) {
            Log.d("", e.getMessage());
        }

        //Implemento el setOnItemSelectedListener: para realizar acciones cuando se seleccionen los ítems
        spinnerDocType.setOnItemSelectedListener(this);
        //Convierto la variable List<> en un ArrayList<>()
        List<String> listaDocs = new ArrayList<>();
        //Almaceno el tamaño de la lista getAllPaises()

        //Agrego los nombres de los países obtenidos y lo almaceno en  `listaPaisesSql`
        listaDocs.add(StringConstant.DEFAULT_DOCTYPE_SPINNER);
        for (int i = 0; i < lstUnits.size(); i++) {
            listaDocs.add(lstUnits.get(i).getDescription());
        }
        //Implemento el adapter con el contexto, layout, listaPaisesSql
        ArrayAdapter<String> adapterDocType = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaDocs);
        //Cargo el spinner con los datos
        spinnerDocType.setAdapter(adapterDocType);
        if (initialUnit != null) {
            spinnerDocType.setSelection(adapterDocType.getPosition(initialUnit));
        }
    }

    private void initView() {
        TextInputLayout tilo = findViewById(R.id.product_create_lblCode);
        tilo.getEditText().setText(product.getCode());
        tilo = findViewById(R.id.product_create_lblName);
        tilo.getEditText().setText(product.getName());
        tilo = findViewById(R.id.product_create_lblDesc);
        tilo.getEditText().setText(product.getDescription());
        tilo = findViewById(R.id.product_create_lblPrice);
        tilo.getEditText().setText(product.getPrice().toString());
        RadioButton btnPrd = findViewById(R.id.product_create_productType_product);
        btnPrd.setClickable(saveAction);
        RadioButton btnSrv = findViewById(R.id.product_create_productType_product);
        btnSrv.setClickable(saveAction);
        if (Product.IDENTIFICATOR_PRODUCT.equals(product.getProductType())) {
            btnPrd.setChecked(true);
        } else {
            btnSrv.setChecked(true);
        }
        initSpinner(product.getUnit() != null ? product.getUnit().getDescription() : null);
    }

    private boolean validateField(ArrayList<TextInputLayout> texts) {
        boolean hasError = false;
        for (TextInputLayout tilo : texts) {
            String text = tilo.getEditText().getText().toString();
            if (text == null || text.isEmpty()) {
                tilo.setError(StringConstant.DATA_CANT_BE_EMPTY);
                hasError = true;
            }
        }
        return hasError;
    }

    public void save() {
        ArrayList<TextInputLayout> texts = new ArrayList<>();
        texts.add((TextInputLayout) findViewById(R.id.product_create_lblCode));
        texts.add((TextInputLayout) findViewById(R.id.product_create_lblName));
        texts.add((TextInputLayout) findViewById(R.id.product_create_lblDesc));
        texts.add((TextInputLayout) findViewById(R.id.product_create_lblPrice));

        boolean hasErros = validateField(texts);

        if (!hasErros) {
            product.setCode(((TextInputLayout) findViewById(R.id.product_create_lblCode)).getEditText().getText().toString());
            product.setName(((TextInputLayout) findViewById(R.id.product_create_lblName)).getEditText().getText().toString());
            product.setDescription(((TextInputLayout) findViewById(R.id.product_create_lblDesc)).getEditText().getText().toString());
            product.setPrice(new Double(((TextInputLayout) findViewById(R.id.product_create_lblPrice)).getEditText().getText().toString()));
            product.getUnit().setOid(oidUnit);
            RadioButton btn = (RadioButton) findViewById(R.id.product_create_productType_product);
            if (btn.isChecked()) {
                product.setProductType(Product.IDENTIFICATOR_PRODUCT);
            } else {
                product.setProductType(Product.IDENTIFICATOR_SERVICE);
            }
            try {
                if (saveAction) {
                    ProductDAO.getInstance().createProduct(product);
                } else {
                    ProductDAO.getInstance().updateProduct(product);
                }
                Toast.makeText(getApplicationContext(), "El producto de Guardo correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Se produjo un error al guardar los datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Se produjo un error al guardar los datos", Toast.LENGTH_SHORT).show();
        }
    }
}
