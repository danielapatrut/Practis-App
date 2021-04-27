package com.techdevs.practis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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


public class MainPageFragment extends Fragment implements MainPageRecyclerAdapter.ListItemClickListener{

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

        mRecyclerAdapter = new MainPageRecyclerAdapter(pageTiles, this);
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

        return view;
    }
    public void onListItemClick(int clickedItemIndex) {
        Page p = pageTiles.get(clickedItemIndex);
        Intent newPageIntent=new Intent(getActivity(),NewPageActivity.class);
        newPageIntent.putExtra("PAGE_TITLE",p.getTitle());
        newPageIntent.putExtra("PAGE_CONTENT",p.getContent());
        newPageIntent.putExtra("PAGE_ID",p.getPageID());
        newPageIntent.putExtra("HAS_PHOTO",p.isHasCoverImage());
        newPageIntent.putExtra("IS_NEW",p.isNewPage());
        //go to new page
        startActivity(newPageIntent);
    }

}