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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.ort.profesionalinvoicemanager.DAO.ClientDAO;
import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.model.client.Client;
import com.ort.profesionalinvoicemanager.views.Utils.StringConstant;
import com.ort.profesionalinvoicemanager.views.Utils.ValidateHelper;
import com.ort.profesionalinvoicemanager.views.ui.ClientList.ClientListFragment;

import java.util.ArrayList;


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
    private static final String ARG_CLIENT = "CLIENT";

    // TODO: Rename and change types of parameters
    private Client client;

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
     * @param client Parameter 1.
     * @return A new instance of fragment ClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientFragment newInstance(Client client) {
        ClientFragment fragment = new ClientFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CLIENT, client);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.client = (Client) getArguments().getSerializable(ARG_CLIENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        configView(view);
        if(this.client != null){
            this.tiloName.getEditText().setText(client.getName());
            this.tiloLastName.getEditText().setText(client.getLastName());
            this.tiloMail.getEditText().setText(client.getMail());
            this.tiloAdress.getEditText().setText(client.getAddress());
        }
        return view;
    }

    private void configView(View view) {
        FloatingActionButton fab = view.findViewById (R.id.client_fab_save);
        fab.setOnClickListener(this);
        fab.show();
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
        if(this.client == null){
            this.taxInfoFragment = new TaxInformationFragment();
        } else {
            this.taxInfoFragment  = new TaxInformationFragment().newInstance(client.getTaxInformation());
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.taxClientFragment, taxInfoFragment).commit();
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

    @Override
    public void onClick(View view) {
        boolean hasError;
        ArrayList<TextInputLayout> texts=new ArrayList<>();
        texts.add(tiloName);
        texts.add(tiloLastName);
        texts.add(tiloMail);
        texts.add(tiloAdress);
        hasError=validateField(texts);
        if (!hasError&&!ValidateHelper.isEmailValid(tiloMail.getEditText().getText().toString())) {
            tiloMail.setError(StringConstant.INVALID_EMAIL);
            hasError = Boolean.TRUE;
        }

        Boolean  taxInfoFragmentHasError = taxInfoFragment.validateFields();


        if(hasError||taxInfoFragmentHasError) {
           showError();
        } else {
            bindAndSave();
        }

    }

    private void bindAndSave() {
        try {
            if(this.client == null){
                Client client = new Client( tiloName.getEditText().getText().toString(),
                                            tiloLastName.getEditText().getText().toString(),
                                            tiloAdress.getEditText().getText().toString(),
                                            tiloMail.getEditText().getText().toString(),
                                            taxInfoFragment.bindAndSave());
                ClientDAO.getInstance().saveUser(client);
                Toast.makeText(this.getContext(),
                        StringConstant.CLIENT_CREATED_SUCCESSFULY, Toast.LENGTH_SHORT).show();
            }else{
                this.client.setName(tiloName.getEditText().getText().toString());
                this.client.setLastName(tiloLastName.getEditText().getText().toString());
                this.client.setAddress(tiloAdress.getEditText().getText().toString());
                this.client.setMail(tiloMail.getEditText().getText().toString());
                this.client.setTaxInformation(taxInfoFragment.bindAndSave());
                ClientDAO.getInstance().edit(client);
                Toast.makeText(this.getContext(),
                        StringConstant.CLIENT_EDITED_SUCCESSFULY, Toast.LENGTH_SHORT).show();
            }
            Fragment clientLisFragment = new ClientListFragment();
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, clientLisFragment)
                    .commit();

        } catch (Exception e) {
            showError();
        }


    }

    private void showError() {
        Toast.makeText(this.getContext(),
                StringConstant.DATA_ERROR, Toast.LENGTH_SHORT).show();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
