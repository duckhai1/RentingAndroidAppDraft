package com.example.locationsscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.book2play.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Location2Screen extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int[] IMAGES = {R.drawable.hcmc1, R.drawable.hcmc2, R.drawable.hcmc3};
    String[] NAMES = {"PhatCho Sporting Center", "HieuLon Sporting Center", "BaoDoi Sporting Center"};
    String[] DESCRIPTIONS = {"199 Phan Xich Long Street, Binh Thanh District", "145 Le Duan Street, District 1", "59 Dien Bien Phu Street, District 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location2);


        ListView listView = (ListView) findViewById(R.id.listPlaces);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarLS2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ho Chi Minh city");


        MyAdapter adapter = new MyAdapter(this, NAMES, DESCRIPTIONS, IMAGES);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    String theCenterName = NAMES[position];
                    Intent intent = new Intent(getApplicationContext(), SearchDayScreen.class);
                    intent.putExtra("CENTERNAME", theCenterName);
                    startActivity(intent);
                }
                if (position == 1) {
                    Toast toast = Toast.makeText(getApplicationContext(), "This center is not available right now :(", Toast.LENGTH_SHORT);
                    toast.show();
                }
                if (position == 2) {
                    Toast toast = Toast.makeText(getApplicationContext(), "This center is not available right now :(", Toast.LENGTH_SHORT);
                    toast.show();
                }
                if (position == 3) {
                    Toast toast = Toast.makeText(getApplicationContext(), "This center is not available right now :(", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String[] Name;
        String[] Description;
        int[] Images;

        MyAdapter(Context c, String name[], String description[], int images[]) {
            super(c, R.layout.rows_listview, R.id.textViewName, name);
            this.context = c;
            this.Name = name;
            this.Description = description;
            this.Images = images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.rows_listview, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView centerName = row.findViewById(R.id.textViewName);
            TextView description = row.findViewById(R.id.textViewDescription);

            images.setImageResource(Images[position]);
            centerName.setText(Name[position]);
            description.setText(Description[position]);

            return row;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng hcmc = new LatLng(10.82302, 106.62965);
        mMap.addMarker(new MarkerOptions().position(hcmc).title("Marker in HCMC"));
        float zoomLevel = 11.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmc, zoomLevel));


    }
}
