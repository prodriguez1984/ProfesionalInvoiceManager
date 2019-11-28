package com.ort.profesionalinvoicemanager.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ort.profesionalinvoicemanager.DAO.UserDAO;
import com.ort.profesionalinvoicemanager.model.base.ApplicationContext;
import com.ort.profesionalinvoicemanager.views.ui.ClientList.ClientListFragment;
import com.ort.profesionalinvoicemanager.views.ui.invoice.BillingFragment;
import com.ort.profesionalinvoicemanager.views.ui.invoice.CreateInvoice;
import com.ort.profesionalinvoicemanager.views.ui.products.ProductCreate;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeCustomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeCustomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeCustomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeCustomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeCustomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeCustomFragment newInstance(String param1, String param2) {
        HomeCustomFragment fragment = new HomeCustomFragment();
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
//        return inflater.inflate(R.layout.fragment_home_custom, container, false);
//        configView();
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_home_custom, container, false);
        configView(view);
        return view;
    }

    private void configView(View view) {
        CardView btnCreateClient = (CardView)view.findViewById(R.id.home_customer_card);
        btnCreateClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientFragment nextFrag= new ClientFragment();
                Fragment clientListFragment = new ClientListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag)
                        .commit();
            }
        });
        CardView btnCreateProduct = (CardView)view.findViewById(R.id.home_product_card);
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductCreate.class);
                startActivity(intent);
            }
        });

        CardView btnStatics = (CardView)view.findViewById(R.id.home_statics_card);
        btnStatics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatisticsFragment nextFrag= new StatisticsFragment();
                Fragment clientListFragment = new StatisticsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag)
                        .commit();
            }
        });

        CardView btnIndustry = (CardView)view.findViewById(R.id.home_industry_card);
        btnIndustry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IndustryActivity.class);
                intent.putExtra("EXTRA_USER", UserDAO.getInstance().getCompleteUser(ApplicationContext.getInstance().getLoggedUser().getOid()));
                startActivity(intent);
            }
        });

        CardView btnBill = (CardView)view.findViewById(R.id.home_invoice_card);
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateInvoice.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
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
//
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
