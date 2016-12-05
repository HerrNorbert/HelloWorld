package hu.gdf.norbi.tabbedpagewithfragments;

import java.util.ArrayList;

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
        items.add(item);
    }

    public Boolean remove_item(BasicItem item){
        return items.remove(item);
    }
    public BasicItem get_item(int i){
        return items.get(i);
    }
}
