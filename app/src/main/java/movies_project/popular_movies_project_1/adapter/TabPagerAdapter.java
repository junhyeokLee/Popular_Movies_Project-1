package movies_project.popular_movies_project_1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import movies_project.popular_movies_project_1.ui.FavoriteFragment;
import movies_project.popular_movies_project_1.ui.PopularMovieFragment;
import movies_project.popular_movies_project_1.ui.TopRatedFragment;


public class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                PopularMovieFragment popularMovieFragment = new PopularMovieFragment();
                return popularMovieFragment;
            case 1:
                TopRatedFragment topRatedFragment = new TopRatedFragment();
                return topRatedFragment;
            case 2:
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                return favoriteFragment;

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
