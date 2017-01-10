package hu.gdf.norbi.tabbedpagewithfragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hu.gdf.norbi.tabbedpagewithfragments.items.BasicItem;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class ItemAdapter {
    ArrayList<BasicItem> items;

    public ItemAdapter() {
        items = new ArrayList<BasicItem>();
    }

    public void add_item(BasicItem item){
        if(isAlreadyHave(item)!=-1)
            items.get(isAlreadyHave(item)).addMount();
        else
            items.add(item);
    }

    public void remove_item(BasicItem item){
        if(isAlreadyHave(item)!=-1)
            items.get(isAlreadyHave(item)).reduceMount();

        else
            items.remove(item);
    }
    public BasicItem get_item(int i){
        return items.get(i);
    }
    public int getItemCount(){
     return items.size();
    }
    public boolean isEmpty(){return items.isEmpty();}
    public int isAlreadyHave(BasicItem item){
        return items.indexOf(item);
    }

    public void sortAlphabet(){
        Collections.sort(items, new Comparator<BasicItem>() {
            @Override
            public int compare(BasicItem basicItem, BasicItem t1) {
                return basicItem.getName().compareTo(t1.getName()) ;
            }
        });
    }
}
