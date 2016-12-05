package hu.gdf.norbi.tabbedpagewithfragments.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import hu.gdf.norbi.tabbedpagewithfragments.ItemAdapter;
import hu.gdf.norbi.tabbedpagewithfragments.R;
import hu.gdf.norbi.tabbedpagewithfragments.items.BasicItem;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class WishListFragment extends Fragment {
    static private ItemAdapter wishlist;
    private Button btnAdd;

    private EditText etAddtoList;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wishlist = new ItemAdapter();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.whistlist_fragment,container,false);

        etAddtoList = (EditText) view.findViewById(R.id.etAddToList);etAddtoList.setText("");
        btnAdd = (Button) view.findViewById(R.id.btnAddtoList);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etAddtoList.getText().length()==0) {
                    Toast.makeText(getActivity(),"Please write an item.",Toast.LENGTH_LONG).show();

                }else {
                    wishlist.add_item(new BasicItem(etAddtoList.getText().toString()));
                    CheckBox cb = new CheckBox(getActivity());
                    cb.setText(wishlist.get_item(wishlist.getItemCount() - 1).toString());
                    ((LinearLayout) getView().findViewById(R.id.llWishList)).addView(cb);
                }etAddtoList.setText("");


            }
        });





        return view;
    }
    public void onResume() {
        super.onResume();

       if (wishlist.getItemCount()!=0){
            for(int i =0; i<wishlist.getItemCount()-1; i++){
                CheckBox cb = new CheckBox(getActivity());
                cb.setText(wishlist.get_item(wishlist.getItemCount() - 1).toString());
                ((LinearLayout) getView().findViewById(R.id.llWishList)).addView(cb);
            }
        }
    }

    public void onPause() {
        super.onPause();

    //Toast.makeText(getActivity(), wishlist.getItemCount(), Toast.LENGTH_LONG).show();
    }
}
