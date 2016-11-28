package hu.gdf.norbi.shoppingtime;

/**
 * Created by Norbi on 2016. 11. 28..
 */
//class for goods in the store
public class ShoppingItem extends Item {
    protected int id;

    public ShoppingItem(int prize, String name, String description, int id) {
        super(prize, name, description);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
