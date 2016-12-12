package hu.gdf.norbi.tabbedpagewithfragments.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hu.gdf.norbi.tabbedpagewithfragments.ItemAdapter;
import hu.gdf.norbi.tabbedpagewithfragments.R;
import hu.gdf.norbi.tabbedpagewithfragments.items.CartItem;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class CartFragment extends Fragment {
    static private ItemAdapter cartlist;
    private Button btnScan;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartlist = new ItemAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment,container,false);
        btnScan = (Button) view.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"scaning...",Toast.LENGTH_LONG).show();
                int id=1;
                CartItem cartItem = new CartItem("tv","qrva tv",id,10000);
                TextView tv = new TextView(getActivity());
                tv.setText(cartItem.toString());
                ((LinearLayout) getView().findViewById(R.id.cartLL)).addView(tv);
                cartlist.add_item(cartItem);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (cartlist.getItemCount()!=0){
            for(int i =0; i<cartlist.getItemCount(); i++){
                TextView tv = new TextView(getActivity());
                tv.setText(cartlist.get_item(i).toString());
                ((LinearLayout) getView().findViewById(R.id.cartLL)).addView(tv);
            }
        }
    }
}
