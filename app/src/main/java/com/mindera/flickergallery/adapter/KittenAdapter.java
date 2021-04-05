package com.mindera.flickergallery.adapter;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindera.flickergallery.R;
import com.mindera.flickergallery.to.Photo;
import com.mindera.flickergallery.widgets.ImageMiauView;

import java.util.List;

public class KittenAdapter extends RecyclerView.Adapter<KittenAdapter.ViewHolder> {

    private List<Photo> kittensList;
    private LayoutInflater mInflater;
    private Context context;

    public KittenAdapter(Context context, List<Photo> data) {
        this.mInflater = LayoutInflater.from(context);
        this.kittensList = data;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageMiauView myKittenImage;

        ViewHolder(View itemView) {
            super(itemView);
            myKittenImage = itemView.findViewById(R.id.miau);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myKittenImage.setPhotoId(kittensList.get(position).getId());
        holder.myKittenImage.init(context);
    }

    @Override
    public int getItemCount() {
        return kittensList.size();
    }
}
