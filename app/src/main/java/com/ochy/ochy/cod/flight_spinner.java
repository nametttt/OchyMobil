
package com.ochy.ochy.cod;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ochy.ochy.R;

public class flight_spinner extends LinearLayout{
    private EditText spinnerEditText;
    private ListView spinnerListView;

    public flight_spinner(Context context) {
        super(context);
        initializeViews(context);
    }

    public flight_spinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public flight_spinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_flight_spinner, this);

        spinnerEditText = view.findViewById(R.id.spinnerEditText);
        spinnerListView = view.findViewById(R.id.spinnerListView);

        spinnerEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerListView.getVisibility() == View.VISIBLE) {
                    spinnerListView.setVisibility(View.GONE);
                } else {
                    spinnerListView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setAdapter(ArrayAdapter<String> adapter) {
        spinnerListView.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        spinnerListView.setOnItemClickListener(listener);
    }
}
