package com.schedulingsimulator.schedulingsimulator;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnPeriodicTaskChangeListener} interface
 * to handle interaction events.
 * Use the {@link PeriodicTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeriodicTaskFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "period";
    private static final String ARG_PARAM2 = "computationTime";
    private static final String ARG_PARAM3 = "isNew";

    // TODO: Rename and change types of parameters
    private int periodParam;
    private int computationTimeParam;
    private boolean isNewParam;

    private OnPeriodicTaskChangeListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param period the period of this task
     * @param computationTime computation time of this task
     * @return A new instance of fragment PeriodicTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PeriodicTaskFragment newInstance(int period, int computationTime, boolean isNew) {
        PeriodicTaskFragment fragment = new PeriodicTaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, period);
        args.putInt(ARG_PARAM2, computationTime);
        args.putBoolean(ARG_PARAM3, isNew);
        fragment.setArguments(args);
        return fragment;
    }

    public PeriodicTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            periodParam = getArguments().getInt(ARG_PARAM1);
            computationTimeParam = getArguments().getInt(ARG_PARAM2);
            isNewParam = getArguments().getBoolean(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_periodic_task, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPeriodicTaskChange(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPeriodicTaskChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPeriodicTaskChangeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPeriodicTaskChangeListener {
        // TODO: Update argument type and name
        public void onPeriodicTaskChange(Uri uri);
    }

    public void save(View view)
    {
        Toast.makeText(getActivity(), "Save periodic task", Toast.LENGTH_SHORT).show();
    }

}
