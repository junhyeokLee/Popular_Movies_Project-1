package movies_project.popular_movies_project_1;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String posterPath;
    private String backdropPath;
    private String plot;
    private String releaseDate;
    private long votesCount;

    public Movie(int id, String title, String posterPath, String plot, String releaseDate, long votesCount, String backdropPath){
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.plot = plot;
        this.releaseDate = releaseDate;
        this.votesCount = votesCount;
        this.backdropPath = backdropPath;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        plot = in.readString();
        releaseDate = in.readString();
        votesCount = in.readLong();
        backdropPath = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPlot() {
        return plot;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public long getVotesCount() {
        return votesCount;
    }

    public String toString(){
        return getTitle() + " " + getId() + " " + getReleaseDate() + "\n" + getPosterPath() + "\n"  + getBackdropPath() +  "\n" + getPlot() + "\n" + getVotesCount() + " votes";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(getId());
        dest.writeString(getTitle());
        dest.writeString(getPosterPath());
        dest.writeString(getPlot());
        dest.writeString(getReleaseDate());
        dest.writeLong(getVotesCount());
        dest.writeString(getBackdropPath());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
