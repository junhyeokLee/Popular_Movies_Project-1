package movies_project.popular_movies_project_1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import movies_project.popular_movies_project_1.R;
import movies_project.popular_movies_project_1.model.Review;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewViewHolder> {

    private Context context;
    private List<Review> mReviewList;

    public ReviewRecyclerAdapter(Context context, List<Review> mReviewList) {
        this.context = context;
        this.mReviewList = mReviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_layout_single_item,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.reviewAuthorText.setText(mReviewList.get(position).getAuthor());
        holder.reviewContentText.setText(mReviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reviewAuthor) TextView reviewAuthorText;
        @BindView(R.id.reviewContent) TextView reviewContentText;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
