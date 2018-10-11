package movies_project.popular_movies_project_1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    private static String POPULAR_MOVIE_DB;
    private static String TOP_RATED_MOVIE_DB;
    private URL mUrl;

    public static ArrayList<Movie> moviesArrayList;
    private String RESULTS = "results";
    private String TITLE = "title";
    private String RELEASE_DATE = "release_date";
    private String AVERAGE_VOTE = "vote_average";
    private String SUMMARY = "overview";
    private String POSTER = "poster_path";
    private String BACKDROP = "backdrop_path";
    private String POSITION = "position";
    private String mMenuUrl;

    GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
    }

    private void initLayout(){

//        movieTitle = new ArrayList<>();
//        releaseDate = new ArrayList<>();
//        averageVote = new ArrayList<>();
//        summary = new ArrayList<>();
//        backdrop = new ArrayList<>();
//        moviePosterImage = new ArrayList<>();

        moviesArrayList = new ArrayList<>();

        POPULAR_MOVIE_DB = getString(R.string.most_popular_url_start) + getResources().getString(R.string.YOUR_API_KEY);
        buildUrl();

        MoviesGridAdapter moviesGridAdapter = new MoviesGridAdapter(MainActivity.this,moviesArrayList);
        mGridView = (GridView)findViewById(R.id.movies_grid);
        mGridView.setAdapter(moviesGridAdapter);

    }
    private void buildUrl() {
        try {
            mUrl = new URL(POPULAR_MOVIE_DB);
        } catch (Exception e) {
            Context context = MainActivity.this;
            String errorMessage = "URL is not found...";
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
        }
        new MovieTask().execute(mUrl);
    }
    // To check if there is a network connection
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    //check network state and populate the UI
    private void load(URL sort){
        if (isOnline()){
            new MovieTask().execute(sort);
        }else {
            Toast.makeText(getApplicationContext(), "please check internet connection, swipe to refresh", Toast.LENGTH_LONG).show();
        }
    }

    private class MovieTask extends AsyncTask<URL,Void,String>{

        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String results = null;

            moviesArrayList = new ArrayList<Movie>();
            Movie newMovie;
            String movieTitle, movieReleaseDate, relativeUrl, moviePlot, moviePosterUrl, backdropPath, movieBackdropurl;
            int movieId;
            long movieVotes;

            try {
                results = NetworkUtils.httpConnectionResponse(url);
                try{
                    JSONObject jsonObject = new JSONObject(results);
                    JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
                    JSONObject movie;
//                    movieTitle = new ArrayList<>(jsonArray.length());
//                    releaseDate = new ArrayList<>(jsonArray.length());
//                    averageVote = new ArrayList<>(jsonArray.length());
//                    summary = new ArrayList<>(jsonArray.length());
//                    backdrop = new ArrayList<>(jsonArray.length());
//                    moviePosterImage = new ArrayList<>(jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        movie = jsonArray.getJSONObject(i);
                        movieId = movie.getInt("id");
                        movieTitle = movie.getString(TITLE);
                        movieReleaseDate = movie.getString(RELEASE_DATE);
                        relativeUrl = movie.getString(POSTER);
                        movieVotes = movie.getLong(AVERAGE_VOTE);
                        moviePlot = movie.getString(SUMMARY);
                        backdropPath = movie.getString(BACKDROP);

//                        movieTitle.add(movie.getString(TITLE));
//                        releaseDate.add(movie.getString(RELEASE_DATE));
//                        averageVote.add(movie.getString(AVERAGE_VOTE));
//                        summary.add(movie.getString(SUMMARY));
//                        backdrop.add(movie.getString(BACKDROP));
//                        moviePosterImage.add(movie.getString(POSTER));
                        newMovie = new Movie(movieId, movieTitle, relativeUrl, moviePlot, movieReleaseDate, movieVotes, backdropPath);
                        moviesArrayList.add(newMovie);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           final Movie movie = null;
            progressDialog.dismiss();
            int orientation = getResources().getConfiguration().orientation;
            mGridView.setNumColumns(orientation == Configuration.ORIENTATION_LANDSCAPE ? 3: 3);
            mGridView.setAdapter(new MoviesGridAdapter(MainActivity.this,moviesArrayList));
            mGridView.setVisibility(View.VISIBLE);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                    Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                    intent.putExtra(POSITION, position);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, mGridView, "simple_transition");
                    startActivity(intent, options.toBundle());
                }

            });
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Please Wait...");
            progressDialog.setMessage("Fetching data...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.most_popular:
                POPULAR_MOVIE_DB = getString(R.string.most_popular_url_start)+getResources().getString(R.string.YOUR_API_KEY);
                try {
                    mUrl = new URL(POPULAR_MOVIE_DB);
                    load(mUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.top_rated:
                TOP_RATED_MOVIE_DB = getString(R.string.top_rated_url_start)+getResources().getString(R.string.YOUR_API_KEY);
                try {
                    mUrl = new URL(TOP_RATED_MOVIE_DB);
                    load(mUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return true;
        }

        return false;
    }
}
