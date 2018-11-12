package movies_project.popular_movies_project_1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import movies_project.popular_movies_project_1.ui.PopularMovieFragment;


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
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 1;
    }
}
