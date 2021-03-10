package com.example.mvvm_room_practice_2.repository;


import android.app.Application;
import android.os.AsyncTask;

import com.example.mvvm_room_practice_2.dao.MoviesDao;
import com.example.mvvm_room_practice_2.database.MoviesDatabase;
import com.example.mvvm_room_practice_2.model.Movies;

import java.util.List;

public class MoviesRepository {

    private MoviesDatabase moviesDatabase;

    public MoviesRepository(Application application) {

        moviesDatabase=MoviesDatabase.getInstance(application);

    }
    public void Insert(List<Movies> moviesList)
    {
        new InsertAsyncTask(moviesDatabase).execute(moviesList);
    }
    static  class InsertAsyncTask extends AsyncTask<List<Movies>,Void,Void>
    {

        private MoviesDao moviesDao;

        private InsertAsyncTask(MoviesDatabase moviesDatabase)
        {
            moviesDao =moviesDatabase.moviesDao();
        }


        @Override
        protected Void doInBackground(List<Movies>... lists) {
            moviesDao.insert(lists[0]);
            return null;
        }
    }

}
