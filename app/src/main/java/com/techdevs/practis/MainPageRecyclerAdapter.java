package com.techdevs.practis;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainPageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int WITH_IMAGE = 0;
    private static int WITHOUT_IMAGE = 1;
    public List<Page> pageTiles;
    public MainPageRecyclerAdapter(List<Page> pageTiles) {
        this.pageTiles=pageTiles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == WITH_IMAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_list_item, parent,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("pressed a tile");
                    //startActivity(new Intent(getApplicationContext(),NewPageActivity.class));
                }
            });
            return new ImageViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_list_item_no_image, parent,false);
            return new NoImageViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(getItemViewType(position)==WITH_IMAGE)
            ((ImageViewHolder)viewHolder).setPageTitle(pageTiles.get(position).getTitle());
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

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView pageTitle;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setPageTitle(String title){
            pageTitle=mView.findViewById(R.id.pageTitle);
            pageTitle.setText(title);
        }

    }
    public class NoImageViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView pageTitle;
        private TextView pageContent;
        public NoImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setPageTitle(String title){
            pageTitle=mView.findViewById(R.id.pageTitle);
            pageTitle.setText(title);
        }
        public void setPageContent(String content){
            pageContent=mView.findViewById(R.id.pageContent);
            pageContent.setText(content);
        }

    }
}
