package movies_project.popular_movies_project_1.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class GridLayoutHelper {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / (int) dpHeight);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
}
