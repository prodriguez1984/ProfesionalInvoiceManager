package com.ort.profesionalinvoicemanager.views.ui.invoice;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ort.profesionalinvoicemanager.DAO.BillingDAO;
import com.ort.profesionalinvoicemanager.DAO.InvoiceDAO;
import com.ort.profesionalinvoicemanager.DAO.ProductDAO;
import com.ort.profesionalinvoicemanager.model.base.AbstractDao;
import com.ort.profesionalinvoicemanager.model.invoice.Invoice;
import com.ort.profesionalinvoicemanager.model.invoice.InvoiceVO;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.DatePickerFragment;
import com.ort.profesionalinvoicemanager.views.R;
import com.ort.profesionalinvoicemanager.views.Utils.PdfHelper;
import com.ort.profesionalinvoicemanager.views.ui.products.ProductAdapter;
import com.ort.profesionalinvoicemanager.views.ui.products.ProductCreate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class BillingFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextInputEditText since;
    private TextInputEditText until;

    public BillingFragment() {
        // Required empty public constructor
    }

    public static BillingFragment newInstance(String param1, String param2) {
        BillingFragment fragment = new BillingFragment();
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
        View view = inflater.inflate(R.layout.fragment_billing, container, false);
        configView(view);
        return view;
    }

    private void configView(View view) {
        since=(TextInputEditText)view.findViewById(R.id.billing_list_txtSince);
        until=(TextInputEditText)view.findViewById(R.id.billing_list_txtUntil);
        GregorianCalendar gc = new GregorianCalendar();
        Date untilDate=gc.getTime();

        gc.add(Calendar.MONTH,-1);
        Date sinceDate=gc.getTime();


        since.setText(AbstractDao.iso8601Format.format(sinceDate));
        until.setText(AbstractDao.iso8601Format.format(untilDate));

        since.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(since);
            }
        });

        until.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(until);
            }
        });

        MaterialButton filterBtn=(MaterialButton)view.findViewById(R.id.billing_list_btnFilter);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<InvoiceVO> list = InvoiceDAO.getInstance().getInvoiceForList(since.getText().toString(),until.getText().toString());
                ((InvoiceAdapter) mAdapter).changeWholeData(list);
            }
        });
       /* btnBillingProccess = (Button)view.findViewById(R.id.btnBillingProccess);
        btnBillingProccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PdfHelper pdfHelper = new PdfHelper();
                Invoice invoice= InvoiceDAO.getInstance().getCompleteInvoiceByOid(OID);
                String pdf =  pdfHelper.getPdf(invoice);
                BillingDAO billingDAO = new BillingDAO();
                billingDAO.SendEmail(pdf,getContext());
            }
        });*/

        //        // 1. get a reference to recyclerView
        recyclerView = view.findViewById(R.id.reciclerProductList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        ArrayList<InvoiceVO> list = InvoiceDAO.getInstance().getInvoiceForList(AbstractDao.iso8601Format.format(sinceDate),AbstractDao.iso8601Format.format(untilDate));
        mAdapter = new InvoiceAdapter(getContext(), list);
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.billing_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), CreateInvoice.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ((InvoiceAdapter)mAdapter).setAddOrEdit(true);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void showDatePickerDialog(final EditText componet) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                String selectedDate= year+ "-" + (month+1) + "-";
                if (day<=9){
                    selectedDate=selectedDate+"0"+ day;
                }else{
                    selectedDate=selectedDate+ day;
                }
                componet.setText(selectedDate);
            }
        });

        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((InvoiceAdapter) mAdapter).isAddOrEdit()){
            ArrayList<InvoiceVO> list = InvoiceDAO.getInstance().getInvoiceForList(since.getText().toString(),until.getText().toString());
            ((InvoiceAdapter) mAdapter).changeWholeData(list);
            ((InvoiceAdapter) mAdapter).setAddOrEdit(false);
        }
    }
}
