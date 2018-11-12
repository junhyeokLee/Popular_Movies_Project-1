package movies_project.popular_movies_project_1.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import movies_project.popular_movies_project_1.db.AppDatabase;
import movies_project.popular_movies_project_1.model.Movie;

public class FavoriteMovieViewModel extends AndroidViewModel {

    AppDatabase  appDatabase;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());
    }

    private static class Insert extends AsyncTask<Movie, Void, Void>{

        private AppDatabase database;

        public Insert(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            database.movieDAO().insertMovie(movies[0]);
            return null;
        }
    }

    public void insertItem(Movie movie){
        new Insert(appDatabase).execute(movie);
    }

    private static class Delete extends AsyncTask<Integer, Void, Void>{

        private AppDatabase database;

        public Delete(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int position = integers[0];
            database.movieDAO().deleteMovieById(position);
            return null;
        }
    }

    public void deleteItem(int id){
        new Delete(appDatabase).execute(id);
    }

}
