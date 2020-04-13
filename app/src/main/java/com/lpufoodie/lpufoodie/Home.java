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
    private static CrystalPreloader crystalPreloader;
    @SuppressLint("StaticFieldLeak")
    private static Context ARG_PARAM1;
    private static FragmentManager ARG_PARAM2;
    private FragmentManager fragmentManager;
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;

    public Home() {
        // Required empty public constructor
    }

    static Home newInstance(FragmentManager param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        ARG_PARAM2 = param2;
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentManager = ARG_PARAM2;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        crystalPreloader = getActivity().findViewById(R.id.loader);
        view.setVisibility(View.INVISIBLE);
        rv = view.findViewById(R.id.rv1);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
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



