package com.example.smart_campus_app1;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment0#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment0 extends Fragment  implements View.OnClickListener {
    private CardView card1,card2,card3,card4,card5;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment0() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment0.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment0 newInstance(String param1, String param2) {
        Fragment0 fragment = new Fragment0();
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
        View v = inflater.inflate(R.layout.fragment_0, container, false);
        card1 = v.findViewById(R.id.Student_M);
        card2 = v.findViewById(R.id.Teacher_M);
        card3 = v.findViewById(R.id.Directory_M);
        card4 = v.findViewById(R.id.Calendar_M);
        card5 = v.findViewById(R.id.Notice_M);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        return v;

    }

    @Override
    public void onClick(View v) {

        Intent i ;

        switch (v.getId()){
            case R.id.Student_M:
                 i = new Intent(Fragment0.this.getActivity(), Student.class);
                startActivity(i);
                break;
            case R.id.Teacher_M:
                i = new Intent(Fragment0.this.getActivity(), Teacher.class);
                startActivity(i);
                break;
            case R.id.Directory_M:
                Toast.makeText(Fragment0.this.getActivity(), "Not added yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Calendar_M:
                Toast.makeText(Fragment0.this.getActivity(), "Not added yet2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Notice_M:
                Toast.makeText(Fragment0.this.getActivity(), "Not added yet3", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}