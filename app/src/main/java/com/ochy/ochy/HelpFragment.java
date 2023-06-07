package com.ochy.ochy;

import static com.ochy.ochy.cod.flightDataList.convertToFlightDataList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ochy.ochy.cod.flightDataList;


public class HelpFragment extends Fragment {

    LinearLayout ln;
    TextView chastye, ofrml_bileta,docs_neccesary,raice_cancel,bilets_delete, oputesh,perelsdetmi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        init(v);
        return v;
    }

    private void init(View v){
        ln = v.findViewById(R.id.mail);
        chastye = v.findViewById(R.id.chastye);
        ofrml_bileta= v.findViewById(R.id.ofrml_bileta);
        docs_neccesary = v.findViewById(R.id.docs_neccesary);
        raice_cancel = v.findViewById(R.id.raice_cancel);
        bilets_delete = v.findViewById(R.id.bilets_delete);
        oputesh = v.findViewById(R.id.oputesh);
        perelsdetmi = v.findViewById(R.id.perelsdetmi);
        chastye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpQuestFragment helpQuestFragment = new HelpQuestFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putInt("varuant", 0);
                helpQuestFragment.variant = 0;
                helpQuestFragment.setArguments(args);
                fragmentTransaction.replace(R.id.framelayout, helpQuestFragment);
                fragmentTransaction.addToBackStack("help");
                fragmentTransaction.commit();
            }
        });


        ofrml_bileta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpQuestFragment helpQuestFragment = new HelpQuestFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putInt("varuant", 1);
                helpQuestFragment.variant = 1;
                helpQuestFragment.setArguments(args);
                fragmentTransaction.replace(R.id.framelayout, helpQuestFragment);
                fragmentTransaction.addToBackStack("help");
                fragmentTransaction.commit();
            }
        });

        docs_neccesary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpQuestFragment helpQuestFragment = new HelpQuestFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putInt("varuant", 2);
                helpQuestFragment.variant = 2;
                helpQuestFragment.setArguments(args);
                fragmentTransaction.replace(R.id.framelayout, helpQuestFragment);
                fragmentTransaction.addToBackStack("help");
                fragmentTransaction.commit();
            }
        });

        raice_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpQuestFragment helpQuestFragment = new HelpQuestFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putInt("varuant", 3);
                helpQuestFragment.variant = 3;
                helpQuestFragment.setArguments(args);
                fragmentTransaction.replace(R.id.framelayout, helpQuestFragment);
                fragmentTransaction.addToBackStack("help");
                fragmentTransaction.commit();
            }
        });

        bilets_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpQuestFragment helpQuestFragment = new HelpQuestFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putInt("varuant", 4);
                helpQuestFragment.variant = 4;

                helpQuestFragment.setArguments(args);
                fragmentTransaction.replace(R.id.framelayout, helpQuestFragment);
                fragmentTransaction.addToBackStack("help");
                fragmentTransaction.commit();
            }
        });


        oputesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpQuestFragment helpQuestFragment = new HelpQuestFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putInt("varuant", 5);
                helpQuestFragment.setArguments(args);
                fragmentTransaction.replace(R.id.framelayout, helpQuestFragment);
                fragmentTransaction.addToBackStack("help");
                fragmentTransaction.commit();
            }
        });


        perelsdetmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpQuestFragment helpQuestFragment = new HelpQuestFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putInt("varuant", 6);
                helpQuestFragment.variant = 6;
                helpQuestFragment.setArguments(args);
                fragmentTransaction.replace(R.id.framelayout, helpQuestFragment);
                fragmentTransaction.addToBackStack("help");
                fragmentTransaction.commit();
            }
        });

        ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","ochy.tickets@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Возник вопрос");
                startActivity(Intent.createChooser(emailIntent, "Напишите нам"));

            }
        });
    }
}