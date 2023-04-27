package com.ochy.ochy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class CardFragment extends Fragment {

    androidx.appcompat.widget.Toolbar mToolBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_card, container, false);
        init(v);
        return v;
    }

    private  void init(View v){
        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Мои карты");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            ProfileFragment pf = new ProfileFragment();
            ((MainActivity) getActivity()).pushFragments("profile", pf);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}