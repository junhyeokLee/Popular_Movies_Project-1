package movies_project.popular_movies_project_1.api;

import movies_project.popular_movies_project_1.model.MovieResult;
import movies_project.popular_movies_project_1.model.ReviewResult;
import movies_project.popular_movies_project_1.model.TrailerResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    //    https://api.themoviedb.org/3/movie/popular?api_key

    @GET("/3/movie/{category}")
    Call<MovieResult> getMovieResult(@Path("category")String category,
                                     @Query("api_key")String api_key);
    @GET("/3/movie/{movie_id}/videos")
    Call<TrailerResult> getTrailerResult(@Path("movie_id")int id, @Query("api_key")String apikey);

    @GET("/3/movie/{movie_id}/reviews")
    Call<ReviewResult> getReviews(@Path("movie_id")int id,@Query("api_key")String apikey);

}
