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

import java.util.ArrayList;

import hu.gdf.norbi.tabbedpagewithfragments.ItemAdapter;
import hu.gdf.norbi.tabbedpagewithfragments.R;
import hu.gdf.norbi.tabbedpagewithfragments.items.WishItem;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class WishListFragment extends Fragment {
    static private ItemAdapter wishlist;
    private Button btnAdd;
    private Button btnSort;
    private EditText etAddtoList;
    private ArrayList<CheckBox> cbArrayList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wishlist = new ItemAdapter();
        cbArrayList = new ArrayList<>();
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
                    WishItem item = new WishItem(etAddtoList.getText().toString());
                    wishlist.add_item(item);
                    if(wishlist.get_item(wishlist.isAlreadyHave(item)).getMount()==1){
                        CheckBox cb = new CheckBox(getActivity());
                        cb.setText(wishlist.get_item(wishlist.getItemCount() - 1).toString());
                        cbArrayList.add(cb);
                        ((LinearLayout) getView().findViewById(R.id.llWishList)).addView(cb);
                    }
                    else{
                        int index = wishlist.isAlreadyHave(item);
                        cbArrayList.get(index).setText(wishlist.get_item(index).toString());
                    }
                }
                etAddtoList.setText("");
            }
        });
        btnSort = (Button) view.findViewById(R.id.btnSortList);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ;
            }
        });
        return view;
    }
    public void onResume() {
        super.onResume();
       if (wishlist.getItemCount()!=0){
            for(int i =0; i<wishlist.getItemCount(); i++){
                CheckBox cb = new CheckBox(getActivity());
                cb.setText(wishlist.get_item(i).toString());
                cb.setChecked( ((WishItem)wishlist.get_item(i)).isGotIt() );
                ((LinearLayout) getView().findViewById(R.id.llWishList)).addView(cb);
                cbArrayList.add(cb);
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tmp = (String) ((CheckBox) view) .getText();
                    }
                });
            }
        }
    }

    public void onPause() {
        super.onPause();
        for(int i = 0; i < cbArrayList.size(); i++){
            ((WishItem)wishlist.get_item(i)).setGotIt(cbArrayList.get(i).isChecked());
            ((LinearLayout) getView().findViewById(R.id.llWishList)).removeView(cbArrayList.get(i));
        }
        cbArrayList.clear();
    //Toast.makeText(getActivity(), wishlist.getItemCount(), Toast.LENGTH_LONG).show();
    }
}
