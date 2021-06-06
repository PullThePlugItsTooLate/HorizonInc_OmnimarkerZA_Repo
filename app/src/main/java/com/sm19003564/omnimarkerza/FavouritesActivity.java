package com.sm19003564.omnimarkerza;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private String[] items = new String[] {"Address", "latitude","longitude"};
    private ArrayAdapter<String> arrayAdapter;
    FavouriteLocationClass fav;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference Ref = database.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        listView = (ListView)  findViewById(R.id.listView);




        Ref.child("FavouriteLocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot myitem: snapshot.getChildren()){
                    fav = myitem.getValue(FavouriteLocationClass.class);
                    arrayList.add(fav.toString());

                }
                arrayAdapter = new ArrayAdapter<String>(FavouritesActivity.this, android.R.layout.simple_list_item_1);
                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}