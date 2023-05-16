package com.ochy.ochy;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class AddDocFragment extends Fragment {
    androidx.appcompat.widget.Toolbar mToolBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_doc, container, false);
        init(v);
        return v;
    }

    private  void init (View v){
        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Добавить документ");
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
                    fragmentManager.popBackStack("profile",0);
                }
            }
        });
    }



}