package com.ochy.ochy;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class BuyTicketSeconStepFragment extends Fragment {


    androidx.appcompat.widget.Toolbar mToolBar;
    View seats_select;
    CheckBox ch_1, ch_2,ch_3, ch_4, ch_5, ch_6, ch_7, ch_8,ch_9, ch_10, ch_11, ch_12,
            ch_13, ch_14,ch_15, ch_16, ch_17, ch_18, ch_19, ch_20,ch_21, ch_22, ch_23, ch_24,
            ch_25, ch_26,ch_27, ch_28, ch_29, ch_30, ch_31, ch_32,ch_33, ch_34, ch_35, ch_36;

    int[] selected_seats;
    ArrayList<Integer> listOfSeats = new ArrayList<Integer>();
    int allSeats=1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy_ticket_secon_step, container, false);

        init(v);

        return v;
    }

    private  void init (View v){
        seats_select = v.findViewById(R.id.seats_select);
        ch_1 = seats_select.findViewById(R.id.chc_1);
        ch_2 = seats_select.findViewById(R.id.chc_2);
        ch_3 = seats_select.findViewById(R.id.chc_3);
        ch_4 = seats_select.findViewById(R.id.chc_4);
        ch_5 = seats_select.findViewById(R.id.chc_5);
        ch_6 = seats_select.findViewById(R.id.chc_6);
        ch_7 = seats_select.findViewById(R.id.chc_7);
        ch_8 = seats_select.findViewById(R.id.chc_8);
        ch_9 = seats_select.findViewById(R.id.chc_9);
        ch_10 = seats_select.findViewById(R.id.chc_10);
        ch_11 = seats_select.findViewById(R.id.chc_11);
        ch_12 = seats_select.findViewById(R.id.chc_12);

        ch_13 = seats_select.findViewById(R.id.chc_13);
        ch_14 = seats_select.findViewById(R.id.chc_14);
        ch_15 = seats_select.findViewById(R.id.chc_15);
        ch_16 = seats_select.findViewById(R.id.chc_16);
        ch_17 = seats_select.findViewById(R.id.chc_17);
        ch_18 = seats_select.findViewById(R.id.chc_18);
        ch_19 = seats_select.findViewById(R.id.chc_19);
        ch_20 = seats_select.findViewById(R.id.chc_20);
        ch_21 = seats_select.findViewById(R.id.chc_21);
        ch_22 = seats_select.findViewById(R.id.chc_22);
        ch_23 = seats_select.findViewById(R.id.chc_23);
        ch_24 = seats_select.findViewById(R.id.chc_24);

        ch_25 = seats_select.findViewById(R.id.chc_25);
        ch_26 = seats_select.findViewById(R.id.chc_26);
        ch_27 = seats_select.findViewById(R.id.chc_27);
        ch_28 = seats_select.findViewById(R.id.chc_28);
        ch_29 = seats_select.findViewById(R.id.chc_29);
        ch_30 = seats_select.findViewById(R.id.chc_30);
        ch_31 = seats_select.findViewById(R.id.chc_31);
        ch_32 = seats_select.findViewById(R.id.chc_32);
        ch_33 = seats_select.findViewById(R.id.chc_33);
        ch_34 = seats_select.findViewById(R.id.chc_34);
        ch_35 = seats_select.findViewById(R.id.chc_35);
        ch_36 = seats_select.findViewById(R.id.chc_36);



        Bundle args = getArguments();
        if (args != null) {
            allSeats = args.getInt("pas");
        }

        selected_seats = new int[allSeats];

        mToolBar = v.findViewById(R.id.toolbar);
        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Покупка авиабилета");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolBar.getNavigationIcon();
        if (navIcon != null) {
            navIcon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            mToolBar.setNavigationIcon(navIcon);
        }
        setHasOptionsMenu(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack("fl",0);
                }
            }
        });
        selectedCheckBox();
    }


    private  void selectedCheckBox(){
        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String chId = getResources().getResourceEntryName(buttonView.getId());
                int numb = Integer.parseInt(chId.split("_")[1]);
                Object o = numb;

                if(!buttonView.isChecked()){
                    listOfSeats.remove(o);
                }

                if (listOfSeats.size()< allSeats){
                    if (buttonView.isChecked()){
                        listOfSeats.add(numb);
                    }
                    else if(!buttonView.isChecked()){
                        listOfSeats.remove(o);
                    }
                }

                else{
                    Toast.makeText(getContext(), "Предельное количество мест", Toast.LENGTH_SHORT).show();
                    buttonView.setChecked(false);
                }


            }
        };

        ch_1.setOnCheckedChangeListener(checkedChangeListener);
        ch_2.setOnCheckedChangeListener(checkedChangeListener);
        ch_3.setOnCheckedChangeListener(checkedChangeListener);
        ch_4.setOnCheckedChangeListener(checkedChangeListener);
        ch_5.setOnCheckedChangeListener(checkedChangeListener);
        ch_6.setOnCheckedChangeListener(checkedChangeListener);
        ch_7.setOnCheckedChangeListener(checkedChangeListener);
        ch_8.setOnCheckedChangeListener(checkedChangeListener);
        ch_9.setOnCheckedChangeListener(checkedChangeListener);
        ch_10.setOnCheckedChangeListener(checkedChangeListener);
        ch_11.setOnCheckedChangeListener(checkedChangeListener);
        ch_12.setOnCheckedChangeListener(checkedChangeListener);
        ch_13.setOnCheckedChangeListener(checkedChangeListener);
        ch_14.setOnCheckedChangeListener(checkedChangeListener);
        ch_15.setOnCheckedChangeListener(checkedChangeListener);
        ch_16.setOnCheckedChangeListener(checkedChangeListener);
        ch_17.setOnCheckedChangeListener(checkedChangeListener);
        ch_18.setOnCheckedChangeListener(checkedChangeListener);
        ch_19.setOnCheckedChangeListener(checkedChangeListener);
        ch_20.setOnCheckedChangeListener(checkedChangeListener);
        ch_21.setOnCheckedChangeListener(checkedChangeListener);
        ch_22.setOnCheckedChangeListener(checkedChangeListener);
        ch_23.setOnCheckedChangeListener(checkedChangeListener);
        ch_24.setOnCheckedChangeListener(checkedChangeListener);
        ch_25.setOnCheckedChangeListener(checkedChangeListener);
        ch_26.setOnCheckedChangeListener(checkedChangeListener);
        ch_27.setOnCheckedChangeListener(checkedChangeListener);
        ch_28.setOnCheckedChangeListener(checkedChangeListener);
        ch_29.setOnCheckedChangeListener(checkedChangeListener);
        ch_30.setOnCheckedChangeListener(checkedChangeListener);
        ch_31.setOnCheckedChangeListener(checkedChangeListener);
        ch_32.setOnCheckedChangeListener(checkedChangeListener);
        ch_33.setOnCheckedChangeListener(checkedChangeListener);
        ch_34.setOnCheckedChangeListener(checkedChangeListener);
        ch_35.setOnCheckedChangeListener(checkedChangeListener);
        ch_36.setOnCheckedChangeListener(checkedChangeListener);

    }
}