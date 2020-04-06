package com.example.locationsscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book2play.R;
import com.example.booking.SelectTimeScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchDayScreen extends AppCompatActivity {

    CalendarView calendarView;
    TextView tv;
    Button bt;
    String date;
    @Override
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchday);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarSD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pick your date!");

        tv = (TextView)findViewById(R.id.centerName);
        tv.setText(getIntent().getStringExtra("CENTERNAME"));

        final ImageView ctnBtn = findViewById(R.id.continueButton);

        calendarView = (CalendarView)findViewById(R.id.calendarView);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date(calendarView.getDate()));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month + 1) + "/" + year;


            }
        });
        ctnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectTimeScreen.class);
                intent.putExtra("THEDATE", (date == null)? currentDate : date);
                startActivity(intent);
            }
        });

    }
}
