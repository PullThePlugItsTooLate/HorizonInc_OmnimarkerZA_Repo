package com.sm19003564.omnimarkerza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
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
    private Button btn_remove_all_favourites;
    private Spinner spin_favourites;

    //Pull favourite places data
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference favouritePlacesRef = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_places);

        btn_remove_favourite = findViewById(R.id.remove_favourite_button);
        btn_remove_all_favourites = findViewById(R.id.remove_all_favourites_button);
        spin_favourites = findViewById(R.id.favourites_spinner);
        List<FavouritePlace> lstFavouritePlaces = new ArrayList<>();

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

        ArrayAdapter<FavouritePlace> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstFavouritePlaces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_favourites.setAdapter(adapter);
    }
}