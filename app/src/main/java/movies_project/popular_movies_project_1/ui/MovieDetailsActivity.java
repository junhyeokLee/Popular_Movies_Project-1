package movies_project.popular_movies_project_1.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movies_project.popular_movies_project_1.BuildConfig;
import movies_project.popular_movies_project_1.R;
import movies_project.popular_movies_project_1.adapter.ReviewRecyclerAdapter;
import movies_project.popular_movies_project_1.adapter.TrailerRecyclerAdapter;
import movies_project.popular_movies_project_1.api.MovieApi;
import movies_project.popular_movies_project_1.api.RetrofitClient;
import movies_project.popular_movies_project_1.model.Movie;
import movies_project.popular_movies_project_1.model.Review;
import movies_project.popular_movies_project_1.model.ReviewResult;
import movies_project.popular_movies_project_1.model.Trailer;
import movies_project.popular_movies_project_1.model.TrailerResult;
import movies_project.popular_movies_project_1.view_model.FavoriteMovieViewModel;
import movies_project.popular_movies_project_1.view_model.MovieViewModel;
import retrofit2.Call;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity{
    @BindView(R.id.movieTitle) TextView titleText;
    @BindView(R.id.releaseDate) TextView releaseDateText;
    @BindView(R.id.rating) RatingBar ratingBar;
    @BindView(R.id.plot) TextView plotText;
    @BindView(R.id.backDropImage) ImageView backDropImageView;
    @BindView(R.id.posterImage) ImageView posterImageView;
    @BindView(R.id.trailerRecyclerList) RecyclerView mTrailerRecyclerList;
    @BindView(R.id.reviewRecyclerList) RecyclerView mReviewRecyclerList;
    private final static String IMAGE_URL = "http://image.tmdb.org/t/p/w500";

    public static String api_key = BuildConfig.API_KEY;
    private static final String TAG = "MovieDetailsActivity";
    Movie movie;

    String mTitle, mReleaseDate , mPlot , mPoster , mBackDrop;
    Double mRating;

    private Toolbar toolbar;

    MovieApi movieApi;

    int mMovie_id;

    TrailerRecyclerAdapter trailerRecyclerAdapter;
    List<Trailer> trailerList;


    ReviewRecyclerAdapter reviewRecyclerAdapter;
    List<Review> reviewList;
    View view;
    Context context;
    private final static String MOVIE_ITEM = "movieItem";

    private final static String TRAILER_LIST = "trailer_list";
    private final static String REVIEW_LIST = "review_list";
    private final static String MOVIE = "movie";
    private final static String FAVORITE = "favorite";
    private final static String YOUTUBE_WEB = "https://www.youtube.com/watch?v=";
    private final static String YOUTUBE_APP = "vnd.youtube:";

    @BindView(R.id.favoriteBtn) ImageView favoriteButton;

    private boolean favorite = false;

    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);


        toolbar = findViewById(R.id.detailBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mReviewRecyclerList = findViewById(R.id.reviewRecyclerList);

        if(savedInstanceState != null){
            trailerList  = savedInstanceState.getParcelableArrayList(TRAILER_LIST);
            reviewList  = savedInstanceState.getParcelableArrayList(REVIEW_LIST);
            movie = savedInstanceState.getParcelable(MOVIE);
            favorite = savedInstanceState.getBoolean(FAVORITE);

        }
        movie = getIntent().getParcelableExtra(MOVIE_ITEM);
        displayUI();

        if(!api_key.isEmpty()){
            displayTrailer();
            displayReview();
        }else {
            Toast.makeText(MovieDetailsActivity.this,"Please Insert your Api key",Toast.LENGTH_LONG).show();
            Log.d(TAG, "Api Key: Please Insert Api Key");
        }


//        getSupportActionBar().setTitle(mTitle);
        collapsingToolbarLayout.setTitle(mTitle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mTrailerRecyclerList.setLayoutManager(layoutManager);
        mTrailerRecyclerList.setHasFixedSize(true);


        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this);
        mReviewRecyclerList.setLayoutManager(reviewLayoutManager);
        mReviewRecyclerList.setHasFixedSize(true);

        loadFavorite();


        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFavorite();
            }
        });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE, movie);
        outState.putBoolean(FAVORITE, favorite);
        outState.putParcelableArrayList(TRAILER_LIST, (ArrayList<? extends Parcelable>) trailerList);
        outState.putParcelableArrayList(REVIEW_LIST, (ArrayList<? extends Parcelable>) reviewList);

    }

    private void loadFavorite() {

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovie(movie.getId()).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(movies != null && movies.size() == 0){
                    favorite = false;
                }else {
                    favorite = true;
                }
                updatefavorite();
            }
        });
    }

    private void updatefavorite() {
        if(favorite){
            favoriteButton.setImageResource(R.mipmap.checked_favorite);
        }else {
            favoriteButton.setImageResource(R.mipmap.unchecked_favorite);
        }
    }

    private void clickFavorite(){
        if(favorite){
            FavoriteMovieViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
            favoriteMovieViewModel.deleteItem(movie.getId());
            favorite = false;
        }else {

            Movie movieModel = new Movie(movie.getId(),movie.getRating(),movie.getTitle(),
                    movie.getPosterPath(),movie.getOriginalTitle(),movie.getBackdropPath(),movie.getSynopsis(), movie.getReleaseDate());

            FavoriteMovieViewModel favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
            favoriteMovieViewModel.insertItem(movieModel);
            favorite = true;
        }

        updatefavorite();
    }


    private void displayTrailer() {
        RetrofitClient retrofitClient = new RetrofitClient();

        movieApi = retrofitClient.getRetrofitClient().create(MovieApi.class);

        Call<TrailerResult> trailerResultCall = movieApi.getTrailerResult(mMovie_id,api_key);
        trailerResultCall.enqueue(new retrofit2.Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                TrailerResult trailerResult = response.body();
                trailerList = trailerResult.getResults();

                trailerRecyclerAdapter = new TrailerRecyclerAdapter(trailerList, new TrailerRecyclerAdapter.TrailerClicked(){

                    @Override
                    public void trailerSelected(String trailerKey) {
                        if(trailerKey != null){
                            startTrailer(trailerKey);
                        }
                    }
                });
                mTrailerRecyclerList.setAdapter(trailerRecyclerAdapter);

            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {

            }
        });

    }

    private void startTrailer(String trailerKey) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_APP + trailerKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_WEB + trailerKey));

        try{
            startActivity(appIntent);
        }catch (ActivityNotFoundException ex){
            startActivity(webIntent);
        }
    }

    private void displayReview() {

        RetrofitClient retrofitClient = new RetrofitClient();

        movieApi = retrofitClient.getRetrofitClient().create(MovieApi.class);

        Call<ReviewResult> reviewResultCall = movieApi.getReviews(mMovie_id,api_key);
        reviewResultCall.enqueue(new retrofit2.Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                ReviewResult reviewResult = response.body();
                reviewList = reviewResult.getResults();

                reviewRecyclerAdapter = new ReviewRecyclerAdapter(context, reviewList);
                mReviewRecyclerList.setAdapter(reviewRecyclerAdapter);

            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {

            }
        });


    }

    private void displayUI() {
        mTitle = movie.getOriginalTitle();
        mReleaseDate = movie.getReleaseDate();
        mPlot = movie.getSynopsis();
        mRating = movie.getRating();
        mPoster = movie.getPosterPath();
        mBackDrop = movie.getBackdropPath();
        mMovie_id = movie.getId();

        float ratingFloat = mRating.floatValue()/2;


        titleText.setText(mTitle);
        releaseDateText.setText(mReleaseDate);
        plotText.setText(mPlot);
        ratingBar.setRating(ratingFloat);

        final String backDropUrl = new StringBuilder().append(IMAGE_URL).append(mBackDrop).toString();

        Picasso.with(this)
                .load(backDropUrl)
                .placeholder(R.drawable.placeholder)
                .into(backDropImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getApplicationContext()).load(backDropUrl)
                                .placeholder(R.drawable.placeholder).into(backDropImageView);
                    }
                });


        final String posterUrl = new StringBuilder().append(IMAGE_URL).append(mPoster).toString();
        Picasso.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .into(posterImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getApplicationContext()).load(posterUrl)
                                .placeholder(R.drawable.placeholder).into(posterImageView);
                    }
                });
    }
}
