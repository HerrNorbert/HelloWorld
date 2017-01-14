package hu.gdf.norbi.tabbedpagewithfragments.fragments;

import android.os.Bundle;import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.gdf.norbi.tabbedpagewithfragments.R;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class MainFragment extends Fragment {
    private TextView textViewLastItem;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);
        return view;
    }
}

