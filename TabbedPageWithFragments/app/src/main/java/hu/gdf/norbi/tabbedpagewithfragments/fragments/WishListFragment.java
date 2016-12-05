package hu.gdf.norbi.tabbedpagewithfragments.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import hu.gdf.norbi.tabbedpagewithfragments.ItemAdapter;
import hu.gdf.norbi.tabbedpagewithfragments.R;
import hu.gdf.norbi.tabbedpagewithfragments.items.BasicItem;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class WishListFragment extends Fragment {
    private ItemAdapter wishlist;
    private Button btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wishlist = new ItemAdapter();
        wishlist.add_item(new BasicItem("tv","fasza kis tévé"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.whistlist_fragment,container,false);
        btn = (Button) view.findViewById(R.id.btnAddtoList);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),wishlist.get_item(0).toString(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
