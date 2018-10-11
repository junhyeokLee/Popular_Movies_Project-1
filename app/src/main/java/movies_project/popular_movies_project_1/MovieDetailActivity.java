package movies_project.popular_movies_project_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    @BindView(R.id.img_backdrop) ImageView mImageViewBackdrop;
    @BindView(R.id.img_poster) ImageView mImageViewPoster;
    @BindView(R.id.tv_movie_title) TextView mTextViewMovieTitle;
    @BindView(R.id.tv_release_date) TextView mTextViewReleasDate;
    @BindView(R.id.tv_avg_rating) TextView mTextViewAvgRating;
    @BindView(R.id.tv_summary) TextView mTextViewSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        mTextViewMovieTitle.setText(MainActivity.moviesArrayList.get(intent.getIntExtra("position",1)).getTitle());
        mTextViewReleasDate.setText(MainActivity.moviesArrayList.get(intent.getIntExtra("position",1)).getReleaseDate());
        mTextViewAvgRating.setText(String.valueOf(MainActivity.moviesArrayList.get(intent.getIntExtra("position",1)).getVotesCount()));
        mTextViewSummary.setText(MainActivity.moviesArrayList.get(intent.getIntExtra("position",1)).getPlot());
        Picasso.with(this)
                .load(getString(R.string.poster_image_url_start) + MainActivity.moviesArrayList.get(intent.getIntExtra("position",1)).getPosterPath())
                .placeholder(R.drawable.ic_error_outline_white_36dp)
                .into(mImageViewPoster);
        Picasso.with(this)
                .load(getString(R.string.backbrop_url_start) + MainActivity.moviesArrayList.get(intent.getIntExtra("position",1)).getBackdropPath())
                .placeholder(R.drawable.ic_error_outline_white_36dp)
                .into(mImageViewBackdrop);
    }
}
