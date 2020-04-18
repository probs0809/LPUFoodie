package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;


public class Search extends Fragment implements MaterialSearchBar.OnSearchActionListener,LpuFoodie {

    private MaterialSearchBar searchBar;

    private FragmentManager fragmentManager;

    public Search(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView rv = view.findViewById(R.id.slv);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());

        searchBar = view.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {


            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Snackbar.make(getActivity().findViewById(R.id.mainActivity), text, Snackbar.LENGTH_LONG).show();
                RestaurantsAdapter ra = new RestaurantsAdapter(getContext(), fragmentManager,view,text.toString());
                rv.setAdapter(ra);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


        RestaurantsAdapter ra = new RestaurantsAdapter(getContext(), fragmentManager,view);
        rv.setAdapter(ra);
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


    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Toast.makeText(getContext(), text.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}
