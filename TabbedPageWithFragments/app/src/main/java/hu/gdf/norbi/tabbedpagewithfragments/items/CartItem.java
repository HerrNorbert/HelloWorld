package hu.gdf.norbi.tabbedpagewithfragments.items;

import java.text.NumberFormat;
import java.util.Locale;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        if (!super.equals(o)) return false;

        CartItem cartItem = (CartItem) o;

        return getId() == cartItem.getId();
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "name='" + name +
                ", description='" + description +
                ", mount=" + mount +
                "id=" + id +
                ", prize=" + NumberFormat.getNumberInstance(Locale.US).format(prize) +
                '}';
    }
}
