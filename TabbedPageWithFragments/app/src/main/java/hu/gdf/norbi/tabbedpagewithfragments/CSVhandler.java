package hu.gdf.norbi.tabbedpagewithfragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import hu.gdf.norbi.tabbedpagewithfragments.items.CartItem;

/**
 * Created by Norbi on 2016. 12. 19..
 */

public class CSVhandler {
    ArrayList<CartItem> avaibleItems;
    String fileName="item_database.csv";
    Context context;
    public CSVhandler(Context context){
        avaibleItems = new ArrayList<>();
        this.context=context;
    }
    public void CsvRead(){
        String currLine="";
        AssetManager assetManager = context.getAssets();
        BufferedReader bufferedReader = null;
        InputStream is = null;
        try {
            is = assetManager.open(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            while ((currLine=bufferedReader.readLine())!=null){
                String[] currItem = currLine.split(";");
                if(currItem.length==4){
                    Log.d("itemName",currItem[0]);
                    Log.d("itemDesc",currItem[1]);
                    Log.d("itemID",currItem[2]);
                    Log.d("itemPrize",currItem[3]);
                    avaibleItems.add(new CartItem(currItem[0],currItem[1],Integer.parseInt(currItem[2]),Integer.parseInt(currItem[3])));
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public CartItem FindItemById(int id){
        CartItem item = new CartItem("","",id,0);
        if(avaibleItems.size()>0){
            if(-1 != avaibleItems.indexOf(item))
                return  avaibleItems.get(avaibleItems.indexOf(item));
        }
        item.setId(-1);
        return item;
    }
}
