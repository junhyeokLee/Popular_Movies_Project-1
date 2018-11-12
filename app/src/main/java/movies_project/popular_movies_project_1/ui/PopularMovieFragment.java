package movies_project.popular_movies_project_1.ui;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movies_project.popular_movies_project_1.BuildConfig;
import movies_project.popular_movies_project_1.R;
import movies_project.popular_movies_project_1.adapter.MovieRecyclerAdapter;
import movies_project.popular_movies_project_1.api.MovieApi;
import movies_project.popular_movies_project_1.api.RetrofitClient;
import movies_project.popular_movies_project_1.model.Movie;
import movies_project.popular_movies_project_1.model.MovieResult;
import movies_project.popular_movies_project_1.utils.GridLayoutHelper;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */

public class PopularMovieFragment extends android.support.v4.app.Fragment {

    public static String api_key = BuildConfig.API_KEY;
    private static final String TAG = "MainActivity";
    public static String popular_category = "popular";
    private final static String MOVIELIST = "movieList";
    MovieApi movieApi;
    GridLayoutHelper gridLayoutHelper;
    @BindView(R.id.movieRecyclerList) RecyclerView mRecyclerList;
    MovieRecyclerAdapter movieRecyclerAdapter;
    List<Movie> movieList;
    private View mView;
    retrofit2.Call<MovieResult> movieResultCall;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            movieList = savedInstanceState.getParcelableArrayList(MOVIELIST);
        }
    }

    public PopularMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(MOVIELIST, (ArrayList<? extends Parcelable>) movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentx

        mView = inflater.inflate(R.layout.fragment_popular_movie, container, false);
        ButterKnife.bind(this, mView);

        gridLayoutHelper = new GridLayoutHelper();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),gridLayoutHelper.calculateNoOfColumns(getContext()));
        mRecyclerList.setLayoutManager(gridLayoutManager);
        mRecyclerList.setHasFixedSize(true);

        if(!api_key.isEmpty()){
            getPopularMovie();
        }else {
            Toast.makeText(getContext(),R.string.insert_api_key,Toast.LENGTH_LONG).show();
            Log.d(TAG, "Api Key: Please Insert Api Key");
        }
        return mView;
    }

    void getPopularMovie(){

        RetrofitClient retrofitClient = new RetrofitClient();

        movieApi = retrofitClient.getRetrofitClient().create(MovieApi.class);

        retrofit2.Call<MovieResult> movieResultCall = movieApi.getMovieResult(popular_category,api_key);

        movieResultCall.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(retrofit2.Call<MovieResult> call, Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                movieList = movieResult.getResults();

                movieRecyclerAdapter = new MovieRecyclerAdapter(getContext(), movieList );
                mRecyclerList.setAdapter(movieRecyclerAdapter);
            }

            @Override
            public void onFailure(retrofit2.Call<MovieResult> call, Throwable t) {
                Toast.makeText(getContext(),"Unable to fetch popular movies, check your internet"
                        ,Toast.LENGTH_LONG).show();
            }
        });


    }



}
