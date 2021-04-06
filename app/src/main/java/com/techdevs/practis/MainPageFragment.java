package com.techdevs.practis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class MainPageFragment extends Fragment {

    private RecyclerView mPagesListView;
    private List<Page> pageTiles;

    public MainPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPagesListView = getActivity().findViewById(R.id.pagesListView);
        pageTiles = new ArrayList<>();
        View view= inflater.inflate(R.layout.fragment_main_page,container,false);

        //connect to firebase

        return view;
    }
}