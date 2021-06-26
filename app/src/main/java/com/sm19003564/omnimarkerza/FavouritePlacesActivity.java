package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavouritePlacesActivity extends AppCompatActivity {

    private Button btn_remove_favourite;

    //Spinner
    private Spinner spin_favourites;
    private FavouritePlace favouritePlaceSelected;
    private List<FavouritePlace> lstFavouritePlaces;
    private ArrayAdapter<FavouritePlace> adapter;

    //Pull favourite places data
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference favouritePlacesRef = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_places);

        btn_remove_favourite = findViewById(R.id.remove_favourite_button);
        spin_favourites = findViewById(R.id.favourites_spinner);
        lstFavouritePlaces = new ArrayList<>();

        populateList();




        spin_favourites.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                btn_remove_favourite.setEnabled(true);
                btn_remove_favourite.setBackgroundResource(R.drawable.custom_button);
                btn_remove_favourite.setTextColor(getResources().getColor(R.color.quantum_black_text) );
                favouritePlaceSelected = (FavouritePlace) adapterView.getSelectedItem();
                //Toast.makeText(FavouritePlacesActivity.this, i + " int", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_remove_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favouritePlaceSelected != null) {
                    favouritePlacesRef.child("FavouritePlaceData").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataInfo: snapshot.getChildren()){
                                FavouritePlace checkFP = dataInfo.getValue(FavouritePlace.class);
                                if (favouritePlaceSelected.getLatitude() == checkFP.getLatitude()
                                        && favouritePlaceSelected.getLongitude() == checkFP.getLongitude()) {
                                    dataInfo.getRef().removeValue();
                                }
                            }

                            Intent i = new Intent(FavouritePlacesActivity.this, MainActivity.class);
                            startActivity(i);
                            Toast.makeText(FavouritePlacesActivity.this, favouritePlaceSelected.getText() + " has been removed from your favourites", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(FavouritePlacesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(FavouritePlacesActivity.this, "Please select a place first", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void populateList() {

        lstFavouritePlaces.clear();
        if (adapter != null) {
            adapter.clear();
            adapter.notifyDataSetChanged();
            spin_favourites.setAdapter(adapter);
        }

        //List population
        favouritePlacesRef.child("FavouritePlaceData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataInfo: snapshot.getChildren()){
                    lstFavouritePlaces.add(dataInfo.getValue(FavouritePlace.class));
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FavouritePlacesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<FavouritePlace> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, lstFavouritePlaces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_favourites.setAdapter(adapter);

    }
}