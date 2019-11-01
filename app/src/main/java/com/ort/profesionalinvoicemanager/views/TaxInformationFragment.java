package com.ort.profesionalinvoicemanager.views;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaxInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaxInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaxInformationFragment extends Fragment {
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
        Spinner spinnerDocType = (Spinner) view.findViewById(R.id.spinnerTaxDocType);;
        //================Datos cargados desde Array=====================//
        //Hago referencia al spinner con el id `sp_frutas`
        //Implemento el setOnItemSelectedListener: para realizar acciones cuando se seleccionen los ítems
//        spinnerDocType.setOnItemSelectedListener(this.getActivity());
        //Convierto la variable List<> en un ArrayList<>()
        ArrayList<String> listaFrutas = new ArrayList<>();
        //Arreglo con nombre de frutas
        String[] strFrutas = new String[] {"Pera", "Manzana", "Fresa", "Sandia", "Mango"};
        //Agrego las frutas del arreglo `strFrutas` a la listaFrutas
        Collections.addAll(listaFrutas, strFrutas);
        //Implemento el adapter con el contexto, layout, listaFrutas
        ArrayAdapter<String> comboAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, listaFrutas);
        //Cargo el spinner con los datos
        spinnerDocType.setAdapter(comboAdapter);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tax_information, container, false);

    }

    private void initTaxSpinner() {

    }
    private void configView(View view) {
//       spinnerDocType = (Spinner) view.findViewById(R.id.taxDocType);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
}
