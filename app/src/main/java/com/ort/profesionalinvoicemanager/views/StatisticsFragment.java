package com.ort.profesionalinvoicemanager.views;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ort.profesionalinvoicemanager.DAO.StatisticsDAO;
import com.ort.profesionalinvoicemanager.model.client.Client;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatisticsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FloatingActionButton btnTotalYear;
    private FloatingActionButton btnTotalMonth,btnMaxClient;
    private EditText etTotalMonth, etTotalYear,etMaxClient,etCantMaxClient;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        configView(view);
        return view;
    }

    private void configView(View view) {
        etTotalYear = view.findViewById(R.id.etTotalYear);
        etTotalMonth = view.findViewById(R.id.etTotalMonth);
        etCantMaxClient= view.findViewById(R.id.etMaxClient);
        btnTotalMonth = (FloatingActionButton) view.findViewById(R.id.fabFigureTotalMonth);
        btnTotalMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cant = StatisticsDAO.getInstance().getTotalMonth();
                etTotalMonth.setText(String.valueOf(cant));
            }
        });
        btnTotalYear = (FloatingActionButton) view.findViewById(R.id.fabFigureTotalYear);
        btnTotalYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cant = StatisticsDAO.getInstance().getTotalYear();
                etTotalYear.setText(String.valueOf(cant));
            }
        });
        btnMaxClient = (FloatingActionButton) view.findViewById(R.id.fabFigureMaxClient);
        btnMaxClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int maxClient = StatisticsDAO.getInstance().getTotalMaxClient();
//                Client client = StatisticsDAO.getInstance().getMaxClient();
//                etMaxClient.setText(String.valueOf(client.getName()));
                etCantMaxClient.setText(StatisticsDAO.getInstance().getTotalMaxClient());
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
