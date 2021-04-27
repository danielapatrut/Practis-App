package com.techdevs.practis;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.GalleryViewHolder>{

    private List<Image> imageTiles;
    public Context context;

    public GalleryRecyclerAdapter(List<Image> imageTiles) {
        this.imageTiles=imageTiles;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent,false);
        context = parent.getContext();
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder viewHolder, int position) {
        viewHolder.setImage(imageTiles.get(position).getUri());
    }

    @Override
    public int getItemCount() {
        return imageTiles.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private ImageView mImage;
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            mImage=mView.findViewById(R.id.image);
        }
        //set image to tile
        public void setImage(String uri){
            mImage=mView.findViewById(R.id.image);
            //Picasso.get().load(uri).into(mImage);
            Glide.with(context).load(uri).into(mImage);
            //mImage.setImageURI(Uri.parse(uri));
        }
    }
}
