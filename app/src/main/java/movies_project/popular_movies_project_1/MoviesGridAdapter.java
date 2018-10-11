package movies_project.popular_movies_project_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesGridAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private ArrayList<Movie> mMovies;
    private TextView mGridText;
    private ImageView mGridImage;
    private View mGrid;

    public MoviesGridAdapter(Context context,ArrayList<Movie> movies){
        super(context,0,movies);
        this.mContext = context;
        this.mMovies = movies;
    }

    public void setData(ArrayList<Movie> movies){
        mMovies = movies;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mMovies == null? 0:mMovies.size();
    }

    @Override
    public Movie getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView == null){
            mGrid = new View(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mGrid = layoutInflater.inflate(R.layout.adapter_movies_grid,null);
        }
        else{
            mGrid = convertView;
        }
        mGridText = mGrid.findViewById(R.id.grid_text);
        mGridImage = mGrid.findViewById(R.id.grid_image);

        mGridText.setText(mMovies.get(position).getTitle());
        Picasso.with(mContext)
                .load(mContext.getString(R.string.poster_image_url_start)+mMovies.get(position).getPosterPath())
                .placeholder(R.drawable.ic_error_outline_white_36dp)
                .into(mGridImage);

        return mGrid;
    }
}
