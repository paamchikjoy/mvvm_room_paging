package com.example.mvvm_room_practice_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mvvm_room_practice_2.adapter.MoviesListAdapter;
import com.example.mvvm_room_practice_2.listeners.Listener;
import com.example.mvvm_room_practice_2.model.Movies;
import com.example.mvvm_room_practice_2.network.Api;
import com.example.mvvm_room_practice_2.repository.MoviesRepository;
import com.example.mvvm_room_practice_2.viewmodel.MoviesViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Listener {
    private MoviesListAdapter moviesListAdapter;
    private MoviesViewModel moviesViewModel;
    private RecyclerView recyclerView;
    private MoviesRepository moviesRepository;
    private PagedList<Movies> pagedList;
    private static final String URL_DATA="http://www.codingwithjks.tech/movies.php/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesRepository=new MoviesRepository(getApplication());
        moviesListAdapter=new MoviesListAdapter(getApplicationContext(),this);
        moviesViewModel=new ViewModelProvider(this).get(MoviesViewModel.class);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);

            moviesViewModel.pagedListLiveData.observe(this, new Observer<PagedList<Movies>>() {
                @Override
                public void onChanged(PagedList<Movies> movies) {
                    moviesListAdapter.submitList(movies);
                    recyclerView.setAdapter(moviesListAdapter);
                     pagedList=movies;
                }
            });

        getAllMovies();
    }

    private void getAllMovies() {
        Retrofit retrofit = new Retrofit
                .Builder().baseUrl(URL_DATA)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api=retrofit.create(Api.class);
        Call<List<Movies>> call = api.getAllMovies();
        call.enqueue(new Callback<List<Movies>>() {
            @Override
            public void onResponse(Call<List<Movies>> call, Response<List<Movies>> response) {

                if (response.isSuccessful())
                {
                    Log.d("Successful", "onResponse: ");
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    moviesRepository.Insert(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Movies>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClickListener(int position) {
        Toast.makeText(this, ""+pagedList.get(position)
                .getMovies_name(), Toast.LENGTH_SHORT).show();
    }
}