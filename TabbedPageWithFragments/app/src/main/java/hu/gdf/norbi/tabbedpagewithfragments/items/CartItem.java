package hu.gdf.norbi.tabbedpagewithfragments.items;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class CartItem extends BasicItem {
    int id, prize;

    public CartItem() {
        id = prize = -1;
    }

    public CartItem(String name, String description, int id, int prize) {
        super(name, description);
        this.id = id;
        this.prize = prize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }
}
