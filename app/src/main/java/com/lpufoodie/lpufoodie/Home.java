package com.lpufoodie.lpufoodie;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystal.crystalpreloaders.widgets.CrystalPreloader;

public class Home extends Fragment implements LpuFoodie {
    @SuppressLint("StaticFieldLeak")
    private FragmentManager fragmentManager;

    Home(FragmentManager fragmentManager){
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
        RestaurantsAdapter ra = new RestaurantsAdapter(getContext(), fragmentManager,view);
        rv.setAdapter(ra);
        return view;
    }
}