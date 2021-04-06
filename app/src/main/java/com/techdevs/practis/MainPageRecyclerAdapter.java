package com.techdevs.practis;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainPageRecyclerAdapter extends RecyclerView.Adapter<MainPageRecyclerAdapter.ViewHolder> {

    public List<Page> pageTiles;
    public MainPageRecyclerAdapter(List<Page> pageTiles) {
        this.pageTiles=pageTiles;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return pageTiles.size();
    }
}
