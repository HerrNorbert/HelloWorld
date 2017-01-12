package hu.gdf.norbi.tabbedpagewithfragments.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import hu.gdf.norbi.tabbedpagewithfragments.MainActivity;
import hu.gdf.norbi.tabbedpagewithfragments.R;
import hu.gdf.norbi.tabbedpagewithfragments.adapters.ItemAdapter;
import hu.gdf.norbi.tabbedpagewithfragments.items.WishItem;

import static hu.gdf.norbi.tabbedpagewithfragments.MainActivity.readFromFile;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class WishListFragment extends Fragment {
    static private ItemAdapter wishlist;
    private Button btnRemove, btnSort;
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

        etAddtoList = (EditText) view.findViewById(R.id.etAddToListName);
        etAddtoList.setText("");
        etAddtoList.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    if (etAddtoList.getText().length()==0) {
                        Toast.makeText(getActivity(),"Please write an item.",Toast.LENGTH_LONG).show();
                    }else {
                        WishItem item = new WishItem(etAddtoList.getText().toString());
                        wishlist.add_item(item);
                        if(wishlist.get_item(wishlist.isAlreadyHave(item)).getMount()==1){
                            final CheckBox cb = new CheckBox(getActivity());
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
                return true;
            }
        });
        btnRemove = (Button) view.findViewById(R.id.btnAddtoList);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean nothingTODel = true;
                for(int i = 0; i < cbArrayList.size();){
                    if(cbArrayList.get(i).isChecked()){
                        ((LinearLayout) getView().findViewById(R.id.llWishList)).removeView(cbArrayList.get(i));
                        cbArrayList.remove(i);
                        wishlist.remove_item(i);
                        nothingTODel = false;
                    }else
                        ++i;
                }
                if(nothingTODel)
                    Toast.makeText(getContext(),"Nothing To delete!",Toast.LENGTH_SHORT).show();
            }
        });
        btnSort = (Button) view.findViewById(R.id.btnSortList);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 0;
                for(CheckBox cb : cbArrayList) {
                    ((WishItem)wishlist.get_item(i)).setGotIt(cb.isChecked());
                    cb.setPaintFlags(0);
                    ++i;
                }
                wishlist.sortAlphabet();
                i = 0;
                for(CheckBox cb : cbArrayList) {
                    cb.setChecked(((WishItem)wishlist.get_item(i)).isGotIt());
                    cb.setText(((WishItem)wishlist.get_item(i)).toString());
                    if(cb.isChecked())
                        cb.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    ++i;
                }
            }
        });
        return view;
    }
    public void onResume() {
        super.onResume();
        if (wishlist.getItemCount()==0)
            readFromFile(wishlist,getContext(),false);
        for(int i =0; i<wishlist.getItemCount(); i++){
            CheckBox cb = new CheckBox(getActivity());
            cb.setText(wishlist.get_item(i).toString());
            cb.setChecked( ((WishItem)wishlist.get_item(i)).isGotIt() );
            if(cb.isChecked())
                cb.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            ((LinearLayout) getView().findViewById(R.id.llWishList)).addView(cb);
            //((LinearLayout) getView().findViewById(R.id.llWishList)).addView(new Button(getContext()) );
            cbArrayList.add(cb);
                /*?áthúzott szögeg????????????????*/
                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tmp = (String) ((CheckBox) view) .getText();
                    }
                });
        }
    }

    public void onPause() {
        super.onPause();
        for(int i = 0; i < cbArrayList.size(); i++){
            ((WishItem)wishlist.get_item(i)).setGotIt(cbArrayList.get(i).isChecked());
            //Log.d("wishlist",((WishItem)wishlist.get_item(i)).toString());
            ((LinearLayout) getView().findViewById(R.id.llWishList)).removeView(cbArrayList.get(i));
        }
        cbArrayList.clear();
        ((MainActivity)getActivity()).writeToFile(wishlist,getContext(),false);
    }

    public static ItemAdapter getWishlist() {
        return wishlist;
    }
}
