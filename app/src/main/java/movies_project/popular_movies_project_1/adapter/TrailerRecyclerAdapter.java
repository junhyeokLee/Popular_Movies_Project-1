package movies_project.popular_movies_project_1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import movies_project.popular_movies_project_1.R;
import movies_project.popular_movies_project_1.model.Trailer;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.TrailerViewHolder> {


    private List<Trailer> mTrailerList;
    private TrailerClicked mTrailerClicked;
    String videoId;
    Context context;
    Trailer trailer;
    private final static String YOUTUBE_WEB = "https://www.youtube.com/watch?v=";



    public TrailerRecyclerAdapter(List<Trailer> mTrailerList, TrailerClicked mTrailerClicked ) {
        this.mTrailerList = mTrailerList;
        this.mTrailerClicked = mTrailerClicked;

    }




    public interface TrailerClicked{
        void trailerSelected(String trailerKey);
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_layout_single_item,parent,false);

        context = parent.getContext();
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailerViewHolder holder, int position) {
//        holder.title.setText(mTrailerList.get(position).getName());

        try{

            String trailkey = mTrailerList.get(position).getKey();


            videoId=extractYoutubeId(YOUTUBE_WEB + trailkey);

            Log.e("VideoId is->","" + videoId);

            final String img_url="http://img.youtube.com/vi/"+videoId+"/0.jpg"; // this is link which will give u thumnail image of that video

            // picasso jar file download image for u and set image in imagview

            Picasso.with(context)
                    .load(img_url)
                    .placeholder(R.drawable.youtube_icon)
                    .into(holder.trailerImageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(context).load(img_url)
                                    .placeholder(R.drawable.youtube_icon).into(holder.trailerImageView);
                        }
                    });

        }catch (MalformedURLException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }





    public class TrailerViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.trailerTitle) TextView title;
        @BindView(R.id.trailerImage) ImageView trailerImageView;



        public TrailerViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int trailerPosition = getAdapterPosition();
                    if(trailerPosition != RecyclerView.NO_POSITION){
                        String trailerkey = mTrailerList.get(trailerPosition).getKey();

                        mTrailerClicked.trailerSelected(trailerkey);
                    }

                }
            });


        }


    }

    public String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }

}
