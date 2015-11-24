package com.schedulingsimulator.schedulingsimulator;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAperiodicTaskChangeListener} interface
 * to handle interaction events.
 * Use the {@link AperiodicTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AperiodicTaskFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "readyTime";
    public static final String ARG_PARAM2 = "computationTime";
    public static final String ARG_PARAM3 = "deadline";
    public static final String ARG_PARAM4 = "isNew";
    public static final String ARG_PARAM5 = "listPosition";

    // TODO: Rename and change types of parameters
    private int readyTimeParam;
    private int computationTimeParam;
    private int deadlineParam;
    private boolean isNewParam = true;
    private int listPositionParam = -1;

    private OnAperiodicTaskChangeListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param readyTime Parameter 1.
     * @param computationTime Parameter 2.
     * @return A new instance of fragment AperiodicTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AperiodicTaskFragment newInstance(int readyTime, int computationTime, int deadline, boolean isNew, int listPosition) {
        AperiodicTaskFragment fragment = new AperiodicTaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, readyTime);
        args.putInt(ARG_PARAM2, computationTime);
        args.putInt(ARG_PARAM3, deadline);
        args.putBoolean(ARG_PARAM4, isNew);
        args.putInt(ARG_PARAM5, listPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public AperiodicTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            readyTimeParam = getArguments().getInt(ARG_PARAM1);
            computationTimeParam = getArguments().getInt(ARG_PARAM2);
            deadlineParam = getArguments().getInt(ARG_PARAM3);
            isNewParam = getArguments().getBoolean(ARG_PARAM4);
            listPositionParam = getArguments().getInt(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aperiodic_task, container, false);
        final EditText readyTimeEditText = (EditText) v.findViewById(R.id.aperiodic_ready_time);
        final EditText computationTimeEditText = (EditText) v.findViewById(R.id.aperiodic_computation_time);
        final EditText deadlineEditText = (EditText) v.findViewById(R.id.aperiodic_deadline);
        if (getArguments() != null)
        {
            readyTimeEditText.setText(Integer.toString(readyTimeParam));
            computationTimeEditText.setText(Integer.toString(computationTimeParam));
            deadlineEditText.setText(Integer.toString(deadlineParam));
        }

        Button saveButton = (Button) v.findViewById(R.id.save_button);
        Button cancelButton = (Button) v.findViewById(R.id.cancel_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAperiodicTaskChange(Integer.parseInt(readyTimeEditText.getText().toString()), Integer.parseInt(computationTimeEditText.getText().toString()), Integer.parseInt(deadlineEditText.getText().toString()), isNewParam, listPositionParam);
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAperiodicTaskChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAperiodicTaskChangeListener");
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
    public interface OnAperiodicTaskChangeListener {
        // TODO: Update argument type and name
        void onAperiodicTaskChange(int readyTime, int computationTime, int deadline, boolean isNew, int listPosition);
    }

}
