package hu.gdf.norbi.tabbedpagewithfragments.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hu.gdf.norbi.tabbedpagewithfragments.CSVhandler;
import hu.gdf.norbi.tabbedpagewithfragments.MainActivity;
import hu.gdf.norbi.tabbedpagewithfragments.R;
import hu.gdf.norbi.tabbedpagewithfragments.adapters.ItemAdapter;
import hu.gdf.norbi.tabbedpagewithfragments.items.BasicItem;
import hu.gdf.norbi.tabbedpagewithfragments.items.CartItem;

import static hu.gdf.norbi.tabbedpagewithfragments.MainActivity.readFromFile;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class CartFragment extends Fragment {
    static private ItemAdapter cartlist;
    private ArrayList<View> tvArrayList;
    private ToggleButton btnScan,btnDelete;
    private Button btnPay,btnClear;
    private TextView tvSpentMoney;
    private int money;
    private CSVhandler handler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartlist = new ItemAdapter();
        tvArrayList = new ArrayList<>();
        handler = new CSVhandler(getContext());
        handler.CsvRead();
       /* ((MainActivity)getActivity()).writeToFile("fasz", getContext());
        ((MainActivity)getActivity()).readFromFile(getContext());*/
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment,container,false);
        tvSpentMoney = (TextView) view.findViewById(R.id.tvSpentMoney);
        btnScan = (ToggleButton) view.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = getActivity();
                if(btnScan.isChecked()){
                    Toast.makeText(getActivity(), R.string.toastNFCoN, Toast.LENGTH_SHORT).show();
                    //((MainActivity)getActivity()).Asd("ads");
                    ((MainActivity)getActivity()).setupForegroundDispatch(activity,((MainActivity)activity).getMyNFCadpter() );
                    //setupForegroundDispatch(this, mNfcAdapter);
                }else{
                    Toast.makeText(getActivity(), R.string.toastNFCoff, Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).stopForegroundDispatch(activity,((MainActivity)activity).getMyNFCadpter() );
                }
            }
        });
        btnPay = (Button) view.findViewById(R.id.BTNpay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),R.string.toastNotSupportedYet, Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete = (ToggleButton) view.findViewById(R.id.BTNdelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnScan.isChecked()){
                    Toast.makeText(getActivity(),R.string.toastScanMustTurnOff,Toast.LENGTH_LONG).show();
                    btnDelete.setChecked(false);
                }
                else{
                    if(btnDelete.isChecked()){
                        for(int i=0;i<tvArrayList.size();++i){
                            ((LinearLayout) getView().findViewById(R.id.llCart)).removeView(tvArrayList.get(i));
                            tvArrayList.remove(i);
                            CheckBox cb = new CheckBox(getActivity());
                            cb.setText(cartlist.get_item(i).toString());
                            tvArrayList.add(i,cb);
                            ((LinearLayout) getView().findViewById(R.id.llCart)).addView(cb);

                        }
                    }else{
                        for(int i=0;i<tvArrayList.size();++i){
                            if( ((CheckBox)tvArrayList.get(i)).isChecked()) {
                                cartlist.remove_item(i);
                            }
                            ((LinearLayout) getView().findViewById(R.id.llCart)).removeView(tvArrayList.get(i));
                        }
                        tvArrayList.clear();
                        money =0;
                        loadViewFromAdapter();
                    }
                    btnScan.setEnabled(!btnScan.isEnabled());
                    btnPay.setEnabled(!btnPay.isEnabled());
                    btnClear.setEnabled(!btnClear.isEnabled());
                }
            }
        });
        btnClear = (Button) view.findViewById(R.id.BTNclear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartlist.clear();
                for(int i=0;i<tvArrayList.size();++i){
                    ((LinearLayout) getView().findViewById(R.id.llCart)).removeView(tvArrayList.get(i));
                }
                tvArrayList.clear();
                money = 0;
                tvSpentMoney.setText(getContext().getString(R.string.spent_money)+": "+ NumberFormat.getNumberInstance(Locale.US).format(money)+getContext().getString(R.string.money_format));
                Log.d("CartFragment","clear");
            }
        });

       if(  ((MainActivity)getActivity()).getReadedNFC() != "" ){
           String[] s = ((MainActivity)getActivity()).getReadedNFC().split(";");
           for ( String line : s ) {

               int id = Integer.parseInt(line);
               if(isCorrectID(id))
                   AddItem(id);
           }
           Log.d("cart",((MainActivity)getActivity()).getReadedNFC()+"!");
           ((MainActivity)getActivity()).clearReadedNFC();
        }
        return view;
    }
    public boolean isCorrectID(int id){
        if (id < 1) {
            Toast.makeText(getActivity(), R.string.toastErrorAtRead, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void AddItemWithView(int id){
        CartItem cartItem = handler.FindItemById(id);//new CartItem("tv","qrva tv",id,prize);
        cartlist.add_item(cartItem);
        if(cartlist.get_item(cartlist.isAlreadyHave(cartItem)).getMount()==1){
            TextView tv = new TextView(getActivity());
            tv.setText(cartItem.toString());
            tvArrayList.add(tv);
            ((LinearLayout) getView().findViewById(R.id.llCart)).addView(tv);
        }else{
            int index = cartlist.isAlreadyHave(cartItem);
            ((TextView)tvArrayList.get(index)).setText(cartlist.get_item(index).toString());
        }
        money += cartItem.getPrize();
        tvSpentMoney.setText(getContext().getString(R.string.spent_money)+": "+ NumberFormat.getNumberInstance(Locale.US).format(money)+getContext().getString(R.string.money_format));
    }
    public void AddItem(int id) {
        //if (handler != null) {
            CartItem cartItem = handler.FindItemById(id);//new CartItem("tv","qrva tv",id,prize);
            cartlist.add_item(cartItem);
        /*}
        else
            Log.d("CartFragment","handler hiba");*/
    }
    public static ItemAdapter getCartlist() {
        return cartlist;
    }
    @Override
    public void onResume() {
        super.onResume();
        money =0;
        if(cartlist.getItemCount()==0)
            readFromFile(cartlist,getContext(),true);
        loadViewFromAdapter();

    }
    public void onPause() {
        for(int i = 0; i < tvArrayList.size(); i++){
            ((LinearLayout) getView().findViewById(R.id.llCart)).removeView(tvArrayList.get(i));
        }
        tvArrayList.clear();
        ((MainActivity)getActivity()).writeToFile(cartlist,getContext(),true);
        super.onPause();
        //btnScan.setChecked(false);
        //((MainActivity)getActivity()).stopForegroundDispatch(getActivity(),((MainActivity)getActivity()).getMyNFCadpter() );
    }
    private void loadViewFromAdapter(){
        for(int i =0; i<cartlist.getItemCount(); i++){
            TextView tv = new TextView(getActivity());
            tv.setText(cartlist.get_item(i).toString());
            tvArrayList.add(tv);
            ((LinearLayout) getView().findViewById(R.id.llCart)).addView(tv);
            money +=  (((CartItem)cartlist.get_item(i)).getPrize()*cartlist.get_item(i).getMount());
        }
        tvSpentMoney.setText(getContext().getString(R.string.spent_money)+": "+ NumberFormat.getNumberInstance(Locale.US).format(money)+getContext().getString(R.string.money_format));
    }
    private void removeItem(){
        BasicItem item = null;
        int index = cartlist.isAlreadyHave(item);
        money -= ((CartItem)item).getPrize();
        cartlist.remove_item(item);
        if( ((CartItem)item).getMount()!=1){
            ((TextView)tvArrayList.get(index)).setText( ((CartItem)cartlist.get_item(index)).toString() );
        }else{
            ((LinearLayout) getView().findViewById(R.id.llCart)).removeView(tvArrayList.get(index));
        }
    }
}
