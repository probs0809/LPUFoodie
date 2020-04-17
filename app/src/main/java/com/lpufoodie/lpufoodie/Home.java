package com.lpufoodie.lpufoodie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalpreloaders.widgets.CrystalPreloader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends Fragment implements LpuFoodie {
    @SuppressLint("StaticFieldLeak")
    private FragmentManager fragmentManager;

    public Home(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        CrystalPreloader crystalPreloader = getActivity().findViewById(R.id.loader);
        view.setVisibility(View.INVISIBLE);
        RecyclerView rv = view.findViewById(R.id.rv1);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());
        crystalPreloader.setVisibility(View.VISIBLE);
        RestaurantDataLoader rd = new RestaurantDataLoader();
        rd.execute(view, fragmentManager, rv, getContext());
        return view;
    }

    static class RestaurantDataLoader extends AsyncTask<Object, Integer, ArrayList<Restaurant>> {
        @Override
        protected ArrayList<Restaurant> doInBackground(Object... o) {
            ArrayList<Restaurant> restaurants = new ArrayList<>();
            LF_DatabaseReference.apply("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot rDataSnap : dataSnapshot.getChildren()) {
                        restaurants.add(rDataSnap.getValue(Restaurant.class));
                    }
                    RestaurantsAdapter ra = new RestaurantsAdapter(restaurants, (Context) o[3], (FragmentManager) o[1]);
                    ((RecyclerView) o[2]).setAdapter(ra);
                    ((View) o[0]).setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return restaurants;
        }

    }
}