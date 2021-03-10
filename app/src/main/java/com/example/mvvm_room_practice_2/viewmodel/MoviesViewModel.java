package com.example.mvvm_room_practice_2.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.mvvm_room_practice_2.dao.MoviesDao;
import com.example.mvvm_room_practice_2.database.MoviesDatabase;
import com.example.mvvm_room_practice_2.model.Movies;

public class MoviesViewModel extends AndroidViewModel {
private MoviesDao moviesDao;
    public LiveData<PagedList<Movies>> pagedListLiveData;
    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesDao= MoviesDatabase.getInstance(application).moviesDao();
        pagedListLiveData=new LivePagedListBuilder<>(
                moviesDao.getAllMovies(),5).build();

    }
}
