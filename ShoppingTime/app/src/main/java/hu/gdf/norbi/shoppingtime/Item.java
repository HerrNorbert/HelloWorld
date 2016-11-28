package hu.gdf.norbi.shoppingtime;

/**
 * Created by Norbi on 2016. 11. 28..
 */
//basic class for items
public class Item {
    protected int Prize;
    protected String Name;
    protected String Description;

    public Item() {
        Prize = 0;
        Name = "unknow";
        Description = "unknow";
    }

    public Item(String name, String description) {
        Prize = 0;
        Name = name;
        Description = description;
    }

    public Item(int prize, String name, String description) {
        Prize = prize;
        Name = name;
        Description = description;
    }

    public int getPrize() {
        return Prize;
    }

    public void setPrize(int prize) {
        Prize = prize;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
