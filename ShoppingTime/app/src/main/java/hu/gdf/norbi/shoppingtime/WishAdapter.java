package hu.gdf.norbi.shoppingtime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Norbi on 2016. 11. 29..
 */

public class WishAdapter extends BaseAdapter{
    private final ArrayList<Item> alWishList;

    public WishAdapter(final Context context, final ArrayList<Item> aItem) {
        alWishList = aItem;
    }

    public void addItem(Item item){
        alWishList.add(item);
    }
    @Override
    public int getCount() {
        return alWishList.size();
    }

    @Override
    public Object getItem(int i) {
        return alWishList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final Item item = alWishList.get(i);
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.itemrow, null);
        //layout elemeinek beallitasa

        TextView textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        textViewName.setText(item.getName());
        TextView textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
        textViewDescription.setText(item.getDescription());

        return itemView;
    }
    public void deleteRow(Item item) {
        if(alWishList.contains(item)) {
            alWishList.remove(item);
        }
    }
}
