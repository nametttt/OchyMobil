package com.ochy.ochy.cod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ochy.ochy.R;
import com.ochy.ochy.cardAdapterListView;

import java.util.ArrayList;

public class flightAdapterListView extends ArrayAdapter<flightDataList> {private cardAdapterListView.OnItemDeleteListener onItemDeleteListener;

    public flightAdapterListView(@NonNull Context context, ArrayList<flightDataList> arrayList){
        super(context, 0, arrayList);
    }


    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }

    public void setOnItemDeleteListener(cardAdapterListView.OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.flight_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        flightDataList currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same


        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.cost);
        textView1.setText(currentNumberPosition.getCost());

        TextView textView2 = currentItemView.findViewById(R.id.marshr);
        textView2.setText(currentNumberPosition.getEzda());

        TextView textView3 = currentItemView.findViewById(R.id.vremya);
        textView3.setText(currentNumberPosition.getDate());

        TextView textView4 = currentItemView.findViewById(R.id.free_places);
        textView4.setText(currentNumberPosition.getPlaces());

        TextView textView5 = currentItemView.findViewById(R.id.durations);
        textView5.setText(currentNumberPosition.getDuration());

        // then return the recyclable view
        return currentItemView;
    }
}
