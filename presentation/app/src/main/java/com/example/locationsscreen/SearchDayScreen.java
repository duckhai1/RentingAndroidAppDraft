package com.example.locationsscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchDayScreen extends AppCompatActivity {

    CalendarView calendarView;
    TextView tv;
    Button bt;

    @Override
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchday);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarLS2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pick your date!");

        tv = (TextView)findViewById(R.id.centerName);
        tv.setText(getIntent().getStringExtra("CENTERNAME"));

        bt = (Button) findViewById(R.id.btnContinue2);

        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                final String date = dayOfMonth + "/" + (month + 1) + "/" + year;

                bt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(SearchDayScreen.this, date, Toast.LENGTH_SHORT).show();
                }
                });
            }
        });

    }
}
