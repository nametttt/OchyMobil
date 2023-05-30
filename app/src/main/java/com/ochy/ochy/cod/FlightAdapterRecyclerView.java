package com.ochy.ochy.cod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ochy.ochy.R;

import java.util.ArrayList;

public class FlightAdapterRecyclerView extends RecyclerView.Adapter<FlightAdapterRecyclerView.ViewHolder> {

    private ArrayList<flightDataList> flightList;
    private Context context;

    public FlightAdapterRecyclerView(Context context, ArrayList<flightDataList> flightList) {
        this.context = context;
        this.flightList = flightList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        flightDataList currentFlight = flightList.get(position);

        holder.textViewCost.setText(currentFlight.getCost());
        holder.textViewMarshr.setText(currentFlight.getEzda());
        holder.textViewVremya.setText(currentFlight.getDate());
        holder.textViewFreePlaces.setText(currentFlight.getPlaces());
        holder.textViewDurations.setText(currentFlight.getDuration());
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCost, textViewMarshr, textViewVremya, textViewFreePlaces, textViewDurations;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCost = itemView.findViewById(R.id.cost);
            textViewMarshr = itemView.findViewById(R.id.marshr);
            textViewVremya = itemView.findViewById(R.id.vremya);
            textViewFreePlaces = itemView.findViewById(R.id.free_places);
            textViewDurations = itemView.findViewById(R.id.durations);
        }
    }
}
