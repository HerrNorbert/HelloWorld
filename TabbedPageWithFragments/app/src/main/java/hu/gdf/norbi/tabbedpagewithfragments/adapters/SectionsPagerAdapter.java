package hu.gdf.norbi.tabbedpagewithfragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.gdf.norbi.tabbedpagewithfragments.fragments.CartFragment;
import hu.gdf.norbi.tabbedpagewithfragments.fragments.MainFragment;
import hu.gdf.norbi.tabbedpagewithfragments.fragments.WishListFragment;

/**
 * Created by Norbi on 2017. 01. 08..
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0 : return new WishListFragment();
            case 1 : return new MainFragment();
            case 2 : return new CartFragment();
            default : return null;//new WishListFragment();
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "WishList";
            case 1:
                return "Main";
            case 2:
                return "Cart";
        }
        return null;
    }
}


