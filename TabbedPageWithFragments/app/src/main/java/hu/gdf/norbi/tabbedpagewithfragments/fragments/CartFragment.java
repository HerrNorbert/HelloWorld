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

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hu.gdf.norbi.tabbedpagewithfragments.CSVhandler;
import hu.gdf.norbi.tabbedpagewithfragments.ItemAdapter;
import hu.gdf.norbi.tabbedpagewithfragments.R;
import hu.gdf.norbi.tabbedpagewithfragments.items.CartItem;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class CartFragment extends Fragment {
    static private ItemAdapter cartlist;
    private ArrayList<TextView> tvArrayList;
    private Button btnScan;
    private TextView tvSpentMoney;
    private int money;
    private CSVhandler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartlist = new ItemAdapter();
        tvArrayList = new ArrayList<>();
        handler = new CSVhandler(getContext());
        try {
            handler.CsvRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment,container,false);
        tvSpentMoney = (TextView) view.findViewById(R.id.tvSpentMoney);
        btnScan = (Button) view.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "scaning...", Toast.LENGTH_LONG).show();
///////////////////////////////////////////////////////////////////////////
                /*                Random rand = new Random();
                int id = rand.nextInt(150) + 1;
                int prize = rand.nextInt(15000) + 1;*/
                //////////////////////////////////////////////////////////
                int id = 1;
                if (id < 1) {
                    Toast.makeText(getActivity(), "error at reading", Toast.LENGTH_LONG).show();
                }else{
                    CartItem cartItem = handler.FindItemById(id);//new CartItem("tv","qrva tv",id,prize);
                    cartlist.add_item(cartItem);
                    if(cartlist.get_item(cartlist.isAlreadyHave(cartItem)).getMount()==1){
                        TextView tv = new TextView(getActivity());
                        tv.setText(cartItem.toString());
                        tvArrayList.add(tv);
                        ((LinearLayout) getView().findViewById(R.id.llCart)).addView(tv);
                    }else{
                        int index = cartlist.isAlreadyHave(cartItem);
                        tvArrayList.get(index).setText(cartlist.get_item(index).toString());
                    }
                    money += cartItem.getPrize();
                    tvSpentMoney.setText(getContext().getString(R.string.spent_money)+": "+ NumberFormat.getNumberInstance(Locale.US).format(money)+getContext().getString(R.string.money_format));
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        money =0;
        if (cartlist.getItemCount()!=0){
            for(int i =0; i<cartlist.getItemCount(); i++){
                TextView tv = new TextView(getActivity());
                tv.setText(cartlist.get_item(i).toString());
                tvArrayList.add(tv);
                ((LinearLayout) getView().findViewById(R.id.llCart)).addView(tv);
                money +=  (((CartItem)cartlist.get_item(i)).getPrize()*cartlist.get_item(i).getMount());
            }
            tvSpentMoney.setText(getContext().getString(R.string.spent_money)+": "+ NumberFormat.getNumberInstance(Locale.US).format(money)+getContext().getString(R.string.money_format));
        }
    }
    public void onPause() {
        super.onPause();
        tvArrayList.clear();
    }
}
