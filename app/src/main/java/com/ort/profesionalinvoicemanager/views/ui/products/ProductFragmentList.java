package com.ort.profesionalinvoicemanager.views.ui.products;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ort.profesionalinvoicemanager.DAO.ProductDAO;
import com.ort.profesionalinvoicemanager.model.product.Product;
import com.ort.profesionalinvoicemanager.views.R;

import java.util.ArrayList;



public class ProductFragmentList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private OnFragmentInteractionListener mListener;

    public ProductFragmentList() {
        // Required empty public constructor
    }

    public static ProductFragmentList newInstance(String param1, String param2) {
        ProductFragmentList fragment = new ProductFragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_fragment_list, container, false);
        //        // 1. get a reference to recyclerView
        recyclerView = rootView.findViewById(R.id.reciclerProductList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        ArrayList<Product> list = ProductDAO.getInstance().getAllWithActiveCondition(true);
        mAdapter = new ProductAdapter(getContext(), list);
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.product_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_showInactive){
            ((ProductAdapter) mAdapter).changeWholeData(ProductDAO.getInstance().getAllWithActiveCondition(false));
        }

        return super.onOptionsItemSelected(item);
    }
}
