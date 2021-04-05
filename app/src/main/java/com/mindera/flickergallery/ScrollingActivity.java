package com.mindera.flickergallery;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import com.mindera.flickergallery.adapter.KittenAdapter;
import com.mindera.flickergallery.options.RetrofitConfig;
import com.mindera.flickergallery.service.RetrofitService;
import com.mindera.flickergallery.to.Kitten;
import com.mindera.flickergallery.to.Photo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollingActivity extends AppCompatActivity {
    String METHOD_SEARCH = "flickr.photos.search";
    int page = 1;
    private RecyclerView rvNumbers;
    private List<Photo> kittenList;
    private KittenAdapter adapter;
    private int counter;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        rvNumbers = findViewById(R.id.rvNumbers);

        callKitten();
    }

    public void callKitten() {
        Call<Kitten> call = new RetrofitConfig().getKittensService().getKittens(METHOD_SEARCH,
                RetrofitService.API_KEY,
                RetrofitService.TAGS_KITTEN,
                page,
                RetrofitService.FORMAT,
                RetrofitService.NO_JSON_CALLBACK);

        call.enqueue(new Callback<Kitten>() {
            @Override
            public void onResponse(Call<Kitten> call, Response<Kitten> response) {
                Kitten miau = response.body();

                kittenList = new ArrayList<>();

                int numberOfColumns = 2;
                GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), numberOfColumns);
                rvNumbers.setLayoutManager(manager);
                rvNumbers.setNestedScrollingEnabled(false);
                rvNumbers.setHasFixedSize(false);

                adapter = new KittenAdapter(getApplicationContext(), kittenList);
                rvNumbers.setAdapter(adapter);

                counter = miau.getPhotos().getPhoto().size() - 1;
                countdown(miau.getPhotos().getPhoto());
            }

            @Override
            public void onFailure(Call<Kitten> call, Throwable t) {
                Log.e("Kitten: ", "Error to search miau:" + t.getMessage());
            }
        });
    }

    public void countdown(List<Photo> miau) {
        if (counter >= 0) {
            countDownTimer = new CountDownTimer(500, 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    kittenList.add(miau.get(counter));
                    kittenList.add(miau.get(counter - 1));
                    adapter.notifyItemChanged(counter);
                    counter = counter -2;
                    countdown(miau);
                }
            }.start();
        }
    }
}