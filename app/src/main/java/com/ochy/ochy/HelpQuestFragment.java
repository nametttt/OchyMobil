package com.ochy.ochy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HelpQuestFragment extends Fragment {
    int variant=0;
    View first, second, third, fouth, fifth, sixth, seventh, eighth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help_quest, container, false);
        init(v);
        return v;
    }

    private void init(View v){
        first = v.findViewById(R.id.first_help);
        second = v.findViewById(R.id.secon_help);
        third = v.findViewById(R.id.third_help);
        fouth = v.findViewById(R.id.foutrh_help);
        Bundle args = new Bundle();
        variant = args.getInt("variant");

        switch (variant){
            case 0:
                first.setVisibility(View.VISIBLE);
                break;

            case 1:
                second.setVisibility(View.VISIBLE);
                break;

            case 2:
                third.setVisibility(View.VISIBLE);
                break;


            case 3:
                fouth.setVisibility(View.VISIBLE);
                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            case 7:

                break;
        }
    }
}