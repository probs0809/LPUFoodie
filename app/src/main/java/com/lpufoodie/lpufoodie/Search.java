package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;


public class Search extends Fragment implements MaterialSearchBar.OnSearchActionListener {

    MaterialSearchBar searchBar;



    public Search() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchBar = view.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            String text;

            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Snackbar.make(getActivity().findViewById(R.id.mainActivity), text, Snackbar.LENGTH_LONG).show();
                this.text = text.toString();
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                Snackbar.make(getActivity().findViewById(R.id.mainActivity), text, Snackbar.LENGTH_LONG).show();
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
