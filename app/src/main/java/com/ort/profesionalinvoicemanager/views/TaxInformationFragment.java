package com.ort.profesionalinvoicemanager.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.DAO.DocTypeDAO;
import com.ort.profesionalinvoicemanager.DAO.IvaCategoryDAO;
import com.ort.profesionalinvoicemanager.DAO.MonotributoCategoryDAO;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.model.tax.DocumentType;
import com.ort.profesionalinvoicemanager.model.tax.IvaCategory;
import com.ort.profesionalinvoicemanager.model.tax.MonotributoCategory;
import com.ort.profesionalinvoicemanager.model.tax.TaxInformation;
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;
import com.ort.profesionalinvoicemanager.views.Utils.ValidateHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaxInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaxInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaxInformationFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerTaxMonoCat;

    private Spinner spinnerDocType;
    private String idDoc, descDoc;
    private List<DocumentType> lstDocumentTypes;

    private Spinner spinnerIvaCategory;
    private String idIva, descIva;
    private List<IvaCategory> lstIvaCategory;

    private TextInputLayout tiloDoc;

    private ArrayAdapter<String> adapterDocType;
    private ArrayAdapter<String> adapterIvaCat;

    private FrameLayout monoFrame;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TAXINFO = "TAXINFO";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private TaxInformation taxInformation;
    private OnFragmentInteractionListener mListener;


    public TaxInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaxInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaxInformationFragment newInstance(TaxInformation taxInfo) {
        TaxInformationFragment fragment = new TaxInformationFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TAXINFO, taxInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.taxInformation = (TaxInformation) getArguments().getSerializable(ARG_TAXINFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_tax_information, container, false);
        configView(view);
        initSpinner();
        if(this.taxInformation != null){
            this.tiloDoc.getEditText().setText(taxInformation.getDocumentNumber());
            int pos = adapterDocType.getPosition(taxInformation.getDocumentType().getDescription());
            this.spinnerDocType.setSelection(pos);
            pos = adapterIvaCat.getPosition(taxInformation.getIva().getDescription());
            this.spinnerIvaCategory.setSelection(pos);
        }
        return view;

    }

    private void initTaxSpinner() {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

                    Toast.makeText(this.getContext(), "Id doc: " + idDoc + " - Desc doc: " + descDoc, Toast.LENGTH_SHORT).show();

                    break;
            }
            //Iva
            switch (adapterView.getId()) {
                case R.id.spinnerTaxIVACat:
                    //Almaceno el id del país seleccionado
                    idIva = lstIvaCategory.get(position).getOid();
                    //Almaceno el nombre del país seleccionado
                    descIva = lstIvaCategory.get(position).getDescription();

                    Toast.makeText(this.getContext(), "Id iva: " + idIva + " - Desc iva: " + descIva, Toast.LENGTH_SHORT).show();

                    break;
            }
            // get spinner value
        }else{
            Toast.makeText(this.getContext(),"Seleccione un valor", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean validateFields() {
        Boolean  tiloDocHasError = validateField(tiloDoc);
        Boolean  DocTypeNameHasError = validateField(spinnerDocType);
        Boolean  IvaCategoryHasError = validateField(spinnerIvaCategory);

        Boolean  hasError = tiloDocHasError ||
                            DocTypeNameHasError ||
                            IvaCategoryHasError;
        return hasError;
    }

    private boolean validateField(Spinner spinner) {
        Boolean hasError = Boolean.FALSE;
        if(spinner.getSelectedItemId() == 0){
            hasError = Boolean.TRUE;
            ((TextView)spinner.getSelectedView()).setError("Error message");
        } else {
            ((TextView)spinner.getSelectedView()).setError(null);
        }
        return hasError;
    }

    private Boolean validateField(TextInputLayout tilo){
        String text = tilo.getEditText().getText().toString();
        Boolean hasError = Boolean.FALSE;
        if (ValidateHelper.validateEmptyString(text)) {
            tilo.setError(StringConstant.DATA_CANT_BE_EMPTY);
            hasError = Boolean.TRUE;
        }else{
            tilo.setError(null);
        }
        return hasError;
    }

    public TaxInformation bindAndSave() {
        TaxInformation taxInfo;
        if(this.taxInformation==null){
            DocumentType docType = new DocumentType(idDoc);
            IvaCategory ivaCat = new IvaCategory(idIva);
            taxInfo = new TaxInformation(null,
                                                        tiloDoc.getEditText().getText().toString(),
                                                        docType,
                                                        ivaCat,
                                                        null);
        } else{
            //this.taxInformation.setIibb("prueba iibb");
            this.taxInformation.setDocumentNumber(tiloDoc.getEditText().getText().toString());
            this.taxInformation.setDocumentType(new DocumentType(idDoc));
            this.taxInformation.setIva(new IvaCategory(idIva));
            taxInfo = this.taxInformation;
        }
        return taxInfo;
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void configView(View view) {
        this.spinnerDocType = (Spinner) view.findViewById(R.id.spinnerTaxDocType);
        this.spinnerIvaCategory =  (Spinner) view.findViewById(R.id.spinnerTaxIVACat);
        this.tiloDoc = view.findViewById(R.id.tilo_tax_Document);
        this.monoFrame = (FrameLayout)view.findViewById(R.id.client_spinner_monocat);

        for (int i = 0; i < this.monoFrame.getChildCount(); i++) {
            View v = this.monoFrame.getChildAt(i);
            v.setVisibility(View.GONE);
            v.postInvalidate();
        }
        this.monoFrame.setVisibility(View.GONE);
        this.monoFrame.postInvalidate();
    }

    private void initSpinner() {
        try {
            lstDocumentTypes =  DocTypeDAO.getInstance().getAll();
            lstIvaCategory = IvaCategoryDAO.getInstance().getAll();

        }catch(Exception e){
            Log.d( "",e.getMessage());
        }

        //Implemento el setOnItemSelectedListener: para realizar acciones cuando se seleccionen los ítems
        spinnerDocType.setOnItemSelectedListener(this);
        //spinnerTaxMonoCat.setOnItemSelectedListener(this);
        spinnerIvaCategory.setOnItemSelectedListener(this);
        //Convierto la variable List<> en un ArrayList<>()
        List<String> listaDocs = new ArrayList<>();
       // List<String> listaCats = new ArrayList<>();
        List<String> listaIva = new ArrayList<>();
        //Almaceno el tamaño de la lista getAllPaises()

        //Agrego los nombres de los países obtenidos y lo almaceno en  `listaPaisesSql`
        listaDocs.add(StringConstant.DEFAULT_DOCTYPE_SPINNER);
        for(int i = 0; i < lstDocumentTypes.size(); i++){
            listaDocs.add(lstDocumentTypes.get(i).getDescription());
        }
        listaIva.add(StringConstant.DEFAULT_IVACAT_SPINNER);
        for(int i = 0; i < lstIvaCategory.size(); i++){
            listaIva.add(lstIvaCategory.get(i).getDescription());
        }
        //Implemento el adapter con el contexto, layout, listaPaisesSql
        this.adapterDocType = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, listaDocs);
        this.adapterIvaCat = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, listaIva);

        //Cargo el spinner con los datos
        spinnerDocType.setAdapter(adapterDocType);
        spinnerIvaCategory.setAdapter(adapterIvaCat);
    }
}
