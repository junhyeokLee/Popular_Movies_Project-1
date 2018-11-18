package movies_project.popular_movies_project_1.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import movies_project.popular_movies_project_1.R;
import movies_project.popular_movies_project_1.adapter.MovieRecyclerAdapter;
import movies_project.popular_movies_project_1.model.Movie;
import movies_project.popular_movies_project_1.utils.GridLayoutHelper;
import movies_project.popular_movies_project_1.view_model.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    MovieRecyclerAdapter movieRecyclerAdapter;
    List<Movie> movieList = new ArrayList<>();

    @BindView(R.id.movieRecyclerList)RecyclerView mRecyclerList;

    private final static String MOVIELIST = "movieList";
    GridLayoutHelper gridLayoutHelper;

    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            movieList = savedInstanceState.getParcelableArrayList(MOVIELIST);
        }
    }

    public FavoriteFragment() {
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
        // Inflate the layout for this fragment

       mView = inflater.inflate(R.layout.fragment_favorite, container, false);

       ButterKnife.bind(this,mView);

       gridLayoutHelper = new GridLayoutHelper();
       GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),gridLayoutHelper.calculateNoOfColumns(getContext()));
       mRecyclerList.setLayoutManager(gridLayoutManager);
       mRecyclerList.setHasFixedSize(true);

       movieRecyclerAdapter = new MovieRecyclerAdapter(getContext(),movieList);
       getFavorite();

       mRecyclerList.setAdapter(movieRecyclerAdapter);
       return mView;
    }

    void getFavorite(){

        movieRecyclerAdapter.clear();

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {


                for (Movie favorite_movie : movies){

                    Movie movie = new Movie(favorite_movie.getId(),favorite_movie.getRating(),
                            favorite_movie.getTitle(), favorite_movie.getPosterPath(),
                            favorite_movie.getOriginalTitle(),favorite_movie.getBackdropPath(),
                            favorite_movie.getSynopsis(), favorite_movie.getReleaseDate());
                   movieList.add(movie);
                }

                movieRecyclerAdapter.notifyDataSetChanged();

            }
        });
    }

}
