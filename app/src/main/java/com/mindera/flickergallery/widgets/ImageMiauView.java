package com.mindera.flickergallery.widgets;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.mindera.flickergallery.options.RetrofitConfig;
import com.mindera.flickergallery.service.RetrofitService;
import com.mindera.flickergallery.to.KittenSizes;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageMiauView extends androidx.appcompat.widget.AppCompatImageView {
    String METHOD_SIZES = "flickr.photos.getSizes";
    String photoId;

    public ImageMiauView(Context context) {
        super(context);
    }

    public ImageMiauView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageMiauView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageMiauView(Context context, String photoId) {
        super(context);

        this.photoId = photoId;
        init(context);
    }

    public void init(Context context) {
        Call<KittenSizes> call = new RetrofitConfig().getKittensService().getKittensSizes(METHOD_SIZES,
                RetrofitService.API_KEY,
                getPhotoId(),
                RetrofitService.FORMAT,
                RetrofitService.NO_JSON_CALLBACK);

        call.enqueue(new Callback<KittenSizes>() {
            @Override
            public void onResponse(Call<KittenSizes> call, Response<KittenSizes> response) {
                KittenSizes miau = response.body();
                setParameters(miau);
            }

            @Override
            public void onFailure(Call<KittenSizes> call, Throwable t) {
                Log.e("Kitten: ", "Error to size miau:" + t.getMessage());
            }
        });
    }

    public void setParameters(KittenSizes miau) {
        this.setTag(photoId);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutParams.height = miau.getSizes().getSize().get(1).getHeight() * 2;
        layoutParams.width = miau.getSizes().getSize().get(1).getWidth() * 2;

        layoutParams.bottomMargin = 16;
        layoutParams.topMargin = 16;
        layoutParams.rightMargin = 16;
        layoutParams.leftMargin = 16;
        this.setLayoutParams(layoutParams);

        this.setAlpha(0f);

        String source = miau.getSizes().getSize().get(1).getSource();
        try {
            Bitmap bitmap = new ImageTask().execute(source).get();
            this.setImageBitmap(bitmap);

            this.animate().alpha(1f).setDuration(500).setListener(new AnimatorListenerAdapter() {});

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getPhotoId() {
        return photoId;
    }

    static class ImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            try {
                java.net.URL url = new java.net.URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
