package com.example.mvvm_room_practice_2.database;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mvvm_room_practice_2.dao.MoviesDao;
import com.example.mvvm_room_practice_2.model.Movies;

@Database(entities = {Movies.class},version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static final String DATABASE_NAME= "MoviesDatabase";


    public abstract MoviesDao moviesDao();
    private static volatile MoviesDatabase INSTANCE=null;

    public static MoviesDatabase getInstance(Context context)
    {
        if (INSTANCE==null)
        {
            synchronized (MoviesDatabase.class)
            {
                if (INSTANCE==null)
                {
                    INSTANCE=Room.databaseBuilder(context,MoviesDatabase.class,DATABASE_NAME)
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;


    }

    static Callback callback=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(INSTANCE).execute();
        }
    };

    static class PopulateAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private MoviesDao moviesDao;

        PopulateAsyncTask(MoviesDatabase moviesDatabase)
        {
            moviesDao=moviesDatabase.moviesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            moviesDao.deleteAll();
            return null;
        }
    }


}
