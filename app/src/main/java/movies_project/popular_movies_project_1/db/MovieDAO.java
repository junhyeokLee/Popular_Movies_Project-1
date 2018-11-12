package movies_project.popular_movies_project_1.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import movies_project.popular_movies_project_1.model.Movie;

@Dao
public interface MovieDAO {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAll();

    @Query("SELECT * FROM movies WHERE id = :movieId")
    LiveData<List<Movie>> getMovieById(int movieId);

    @Insert
    void insertMovie(Movie movie);

    @Query("DELETE FROM movies WHERE id = :movieId")
    void deleteMovieById(int movieId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie moive);

}
