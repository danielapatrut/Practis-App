package com.techdevs.practis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainPageFragment extends Fragment {

    private RecyclerView mPagesListView;
    private List<Page> pageTiles;
    private MainPageRecyclerAdapter mRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public MainPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_main_page,container,false);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        pageTiles = new ArrayList<>();
        mPagesListView = getActivity().findViewById(R.id.pagesListView);

        mRecyclerAdapter = new MainPageRecyclerAdapter(pageTiles);
        mPagesListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPagesListView.setAdapter(mRecyclerAdapter);
        //connect to firebase

        firebaseFirestore.collection("pages")
                .whereEqualTo("userID", firebaseAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document!=null) {
                                    Page page;
                                    page = document.toObject(Page.class);
                                    pageTiles.add(page);
                                    mRecyclerAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            System.out.println("error retrieving pages");
                        }
                    }
                });
        //dummy pages
        /*Page example = new Page("1",true,"Title");
        pageTiles.add(example);
        Page example2 = new Page("2",true,"Title2");
        pageTiles.add(example2);
        Page example3 = new Page("3",true,"Title3");
        pageTiles.add(example3);
        Page example4 = new Page(false,"No Img Title","Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        pageTiles.add(example4);*/
        //mRecyclerAdapter.notifyDataSetChanged();

        return view;
    }
}