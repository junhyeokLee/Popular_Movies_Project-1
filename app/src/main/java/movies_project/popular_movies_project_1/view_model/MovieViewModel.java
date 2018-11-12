package movies_project.popular_movies_project_1.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import movies_project.popular_movies_project_1.db.AppDatabase;
import movies_project.popular_movies_project_1.model.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    AppDatabase appDatabase;

    private LiveData<List<Movie>> movieList;
    private LiveData<List<Movie>> movie;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getInstance(this.getApplication());

        movieList = appDatabase.movieDAO().getAll();
    }

    public LiveData<List<Movie>> getMovies(){
        return  movieList;
    }

    public LiveData<List<Movie>> getMovie(int movieId){
        movie = appDatabase.movieDAO().getMovieById(movieId);
        return  movie;
    }


}
