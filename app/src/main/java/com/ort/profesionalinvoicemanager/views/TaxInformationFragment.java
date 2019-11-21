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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.DAO.DocTypeDAO;
import com.ort.profesionalinvoicemanager.DAO.IvaCategoryDAO;
import com.ort.profesionalinvoicemanager.DAO.MonotributoCategoryDAO;
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
    private List<MonotributoCategory> lstTaxMonoCat;
    private String idMonoCat, descMonoCat;

    private Spinner spinnerDocType;
    private String idDoc, descDoc;
    private List<DocumentType> lstDocumentTypes;

    private Spinner spinnerIvaCategory;
    private String idIva, descIva;
    private List<IvaCategory> lstIvaCategory;

    private TextInputLayout tiloDoc;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    /*private String mParam1;
    private String mParam2;*/

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
    public static TaxInformationFragment newInstance() {
        TaxInformationFragment fragment = new TaxInformationFragment();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tax_information, container, false);
        configView(view);
        initSpinner();
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
            //Category
            switch (adapterView.getId()) {
                case R.id.spinnerTaxMonoCat:
                    //Almaceno el id del país seleccionado
                    idMonoCat = lstTaxMonoCat.get(position).getOid();
                    //Almaceno el nombre del país seleccionado
                    descMonoCat = lstTaxMonoCat.get(position).getCategory();

                    Toast.makeText(this.getContext(), "Id doc: " + idMonoCat + " - Desc doc: " + descMonoCat, Toast.LENGTH_SHORT).show();

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
        Boolean  TaxMonoCatHasError = validateField(spinnerTaxMonoCat);
        Boolean  IvaCategoryHasError = validateField(spinnerIvaCategory);

        Boolean  hasError = tiloDocHasError ||
                            DocTypeNameHasError ||
                            TaxMonoCatHasError ||
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
        if (!ValidateHelper.validateEmptyString(text)) {
            tilo.setError(StringConstant.DATA_CANT_BE_EMPTY);
            hasError = Boolean.TRUE;
        }else{
            tilo.setError(null);
        }
        return hasError;
    }

    public TaxInformation bindAndSave() {
        TaxInformation taxInfo = new TaxInformation(tiloDoc.getEditText().getText().toString(),
                                                    spinnerDocType.getId(),
                                                    spinnerIvaCategory.getId(),
                                                    spinnerTaxMonoCat.getId())
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
        this.spinnerTaxMonoCat =  (Spinner) view.findViewById(R.id.spinnerTaxMonoCat);
        this.spinnerIvaCategory =  (Spinner) view.findViewById(R.id.spinnerTaxIVACat);
        this.tiloDoc = view.findViewById(R.id.tilo_tax_Document);

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
        ArrayAdapter<String> adapterDocType = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, listaDocs);
        ArrayAdapter<String> adapterMonoCat = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, listaCats);
        ArrayAdapter<String> adapterIvaCat = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, listaIva);

        //Cargo el spinner con los datos
        spinnerDocType.setAdapter(adapterDocType);
        spinnerTaxMonoCat.setAdapter(adapterMonoCat);
        spinnerIvaCategory.setAdapter(adapterIvaCat);
    }
}
