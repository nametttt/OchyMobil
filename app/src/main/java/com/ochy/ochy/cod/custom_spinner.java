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

public class custom_spinner extends LinearLayout {
    private EditText spinnerEditText;
    private ListView spinnerListView;
    private ArrayAdapter<String> adapter;

    public custom_spinner(Context context) {
        super(context);
        initializeViews(context);
    }

    public custom_spinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public custom_spinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_spinner, this);

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

        spinnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = adapter.getItem(position);
                spinnerEditText.setText(selectedItem);
                spinnerListView.setVisibility(View.GONE);
            }
        });
    }

    public void setAdapter(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
        spinnerListView.setAdapter(adapter);
    }
}
