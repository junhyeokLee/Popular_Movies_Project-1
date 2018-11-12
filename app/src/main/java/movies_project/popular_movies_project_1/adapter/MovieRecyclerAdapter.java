package movies_project.popular_movies_project_1.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movies_project.popular_movies_project_1.R;
import movies_project.popular_movies_project_1.model.Movie;
import movies_project.popular_movies_project_1.ui.MovieDetailsActivity;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private Context context;

    private List<Movie> mMovieList;
    private final static String IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    private final static String movieItem = "movieItem";
    private final static String poster = "poster";

    public MovieRecyclerAdapter(Context context, List<Movie> mMovieList) {
        this.context = context;
        this.mMovieList = mMovieList;
    }

    @NonNull
    @Override
    public MovieRecyclerAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_single_list_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieRecyclerAdapter.MovieViewHolder holder, int position) {

        final String image = mMovieList.get(position).getPosterPath();

        final String imageUrl = new StringBuilder().append(IMAGE_URL).append(image).toString();
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context).load(imageUrl)
                                .placeholder(R.drawable.placeholder).into(holder.imageView);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.singleImageView)
        ImageView imageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Movie itemPosition = mMovieList.get(position);
                        Intent detailIntent = new Intent(context, MovieDetailsActivity.class);
                        detailIntent.putExtra(movieItem,itemPosition);
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) context,imageView,poster);
                        context.startActivity(detailIntent,optionsCompat.toBundle());
                    }
                }
            });

        }

    }


    public void clear(){
        if (mMovieList.size() > 0){
            mMovieList.clear();
        }
    }
}
