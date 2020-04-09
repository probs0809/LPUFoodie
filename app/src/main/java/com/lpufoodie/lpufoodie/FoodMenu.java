package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FoodMenu extends AppCompatActivity {
    private static Restaurant ARG_PARAM1;
    private static Context ARG_PARAM2;
    private static FragmentManager ARG_PARAM3;

    private Restaurant restaurant;
    private Context context;
    private FragmentManager fragmentManager;
    private RecyclerView rv;

    Button cart ;

    public static void newInstance(Restaurant param1, Context context, FragmentManager fragmentManager) {
        ARG_PARAM1 = param1;
        ARG_PARAM2 = context;
        ARG_PARAM3 = fragmentManager;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_food_menu);
        this.restaurant = ARG_PARAM1;
        this.context = ARG_PARAM2;
        this.fragmentManager = ARG_PARAM3;
        MainActivity.restaurant = null;
        cart = findViewById(R.id.go_to_cart);

        rv = findViewById(R.id.rv1);
        rv.setVisibility(View.INVISIBLE);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        final List<Food> food = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Foods/"+this.restaurant.getId());
        MainActivity.restaurant = this.restaurant;
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot rDataSnap: dataSnapshot.getChildren()) {
                    food.add(rDataSnap.getValue(Food.class));
                }

                FoodAdapter ra = new FoodAdapter(food, getApplicationContext(), fragmentManager, (Button) findViewById(R.id.go_to_cart));
                rv.setAdapter(ra);
                rv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void goToCart(View view) {
        Intent i = new Intent(FoodMenu.this ,MainActivity.class);
        i.putExtra("Cart",true);
        startActivity(i);
    }
}
