package com.ort.profesionalinvoicemanager.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.DAO.ClientDAO;
import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;
import com.ort.profesionalinvoicemanager.views.Utils.ValidateHelper;
import com.ort.profesionalinvoicemanager.views.ui.ClientList.ClientListFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnSave;
    private TextInputLayout tiloName;
    private TextInputLayout tiloLastName;
    private TextInputLayout tiloMail;
    private TextInputLayout tiloAdress;
    private TaxInformationFragment taxInfoFragment;

    private OnFragmentInteractionListener mListener;

    public ClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientFragment newInstance(String param1, String param2) {
        ClientFragment fragment = new ClientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        configView(view);
        return view;
    }

    private void configView(View view) {
        this.btnSave = view.findViewById(R.id.clientBtnSave);
        btnSave.setOnClickListener(this);
        this.tiloName = view.findViewById(R.id.tilo_client_name);
        this.tiloLastName = view.findViewById(R.id.tilo_client_lastname);
        this.tiloMail = view.findViewById(R.id.tilo_client_mail);
        this.tiloAdress = view.findViewById(R.id.tilo_client_adress);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.taxInfoFragment = new TaxInformationFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.taxClientFragment, taxInfoFragment).commit();
    }

    @Override
    public void onClick(View view) {

        Boolean  tiloNameHasError = validateField(tiloName);
        Boolean  tiloLastNameHasError = validateField(tiloLastName);
        Boolean  tiloMailHasError = validateField(tiloMail);
        if (!ValidateHelper.isEmailValid(tiloMail.getEditText().getText().toString())) {
            tiloMail.setError(StringConstant.INVALID_EMAIL);
            tiloMailHasError = Boolean.TRUE;
        }
        Boolean  tiloAdressHasError = validateField(tiloAdress);
        Boolean  taxInfoFragmentHasError = taxInfoFragment.validateFields();

        Boolean hasError = tiloNameHasError || tiloMailHasError || tiloLastNameHasError || tiloAdressHasError || taxInfoFragmentHasError;
        if(hasError) {
            showError();
        } else {
            bindAndSave();
        }
    }

    private void bindAndSave() {
        Client client = new Client( tiloName.getEditText().getText().toString(),
                                    tiloLastName.getEditText().getText().toString(),
                                    tiloAdress.getEditText().getText().toString(),
                                    tiloMail.getEditText().getText().toString(),
                                    taxInfoFragment.bindAndSave());
        try {
            ClientDAO.getInstance().saveUser(client);
            Toast.makeText(this.getContext(),
                    StringConstant.CLIENT_CREATED_SUCCESSFULY, Toast.LENGTH_SHORT).show();
            Fragment clientLisFragment = new ClientListFragment();
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, clientLisFragment)
                    .commit();

        } catch (Exception e) {
            showError();
        }


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

    private void showError() {
        Toast.makeText(this.getContext(),
                StringConstant.DATA_ERROR, Toast.LENGTH_SHORT).show();
    }

    /*@Override
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
}
