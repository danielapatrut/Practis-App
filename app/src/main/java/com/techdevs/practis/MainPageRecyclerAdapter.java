package com.techdevs.practis;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainPageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int WITH_IMAGE = 0;
    private static int WITHOUT_IMAGE = 1;
    public List<Page> pageTiles;
    public Context context;
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    final private ListItemClickListener clickHandler;

    public MainPageRecyclerAdapter(List<Page> pageTiles, ListItemClickListener listener) {
        this.pageTiles=pageTiles;
        clickHandler=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        if(viewType == WITH_IMAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_list_item, parent,false);
            return new ImageViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_list_item_no_image, parent,false);
            return new NoImageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position)==WITH_IMAGE){
            ((ImageViewHolder)viewHolder).setPageTitle(pageTiles.get(position).getTitle());
            ((ImageViewHolder)viewHolder).getImage(pageTiles.get(position).getPageID());
        }
        else
        {
            String title = pageTiles.get(position).getTitle();
            String content = pageTiles.get(position).getContent();
            ((NoImageViewHolder)viewHolder).setPageTitle(title);
            ((NoImageViewHolder)viewHolder).setPageContent(content);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(pageTiles.get(position).isHasCoverImage())
            return WITH_IMAGE;
        else return WITHOUT_IMAGE;
    }

    @Override
    public int getItemCount() {
        return pageTiles.size();
    }



    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View mView;
        private TextView pageTitle;
        private ImageView image;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            itemView.setOnClickListener(this);
        }
        //set image to tile
        public void setPageTitle(String title){
            pageTitle=mView.findViewById(R.id.pageTitle);
            pageTitle.setText(title);
        }
        public void setPageImage(String uri){
            image = mView.findViewById(R.id.pageCoverImage);
            Glide.with(context).load(uri).into(image);
        }
        public void getImage(int id){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("pages").whereEqualTo("pageID",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            setPageImage(document.getString("uri"));
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            clickHandler.onListItemClick(getAdapterPosition());
        }
    }
    public class NoImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View mView;
        private TextView pageTitle;
        private TextView pageContent;
        public NoImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            itemView.setOnClickListener(this);
        }
        public void setPageTitle(String title){
            pageTitle=mView.findViewById(R.id.pageTitle);
            pageTitle.setText(title);
        }
        public void setPageContent(String content){
            pageContent=mView.findViewById(R.id.pageContent);
            pageContent.setText(Html.fromHtml(content).toString());
        }
        @Override
        public void onClick(View v) {
            clickHandler.onListItemClick(getAdapterPosition());
        }

    }

}
