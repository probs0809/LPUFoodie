package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FoodMenu extends Fragment {
    private static Restaurant ARG_PARAM1;
    private static Context ARG_PARAM2;
    private static FragmentManager ARG_PARAM3;

    private Restaurant restaurant;
    private Context context;
    private FragmentManager fragmentManager;
    private RecyclerView rv;

    public FoodMenu() {
        // Required empty public constructor
    }
    public static FoodMenu newInstance(Restaurant param1, Context context, FragmentManager fragmentManager) {
        FoodMenu fragment = new FoodMenu();
        Bundle args = new Bundle();
        ARG_PARAM1 = param1;
        ARG_PARAM2 = context;
        ARG_PARAM3 = fragmentManager;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.restaurant = ARG_PARAM1;
            this.context = ARG_PARAM2;
            this.fragmentManager = ARG_PARAM3;

        }
        MainActivity.restaurant = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_food_menu, container, false);
        view.setVisibility(View.INVISIBLE);
        rv = view.findViewById(R.id.rv1);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
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

                FoodAdapter ra = new FoodAdapter(food, getContext(), fragmentManager,(Button) Objects.requireNonNull(getActivity()).findViewById(R.id.go_to_cart));
                rv.setAdapter(ra);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
