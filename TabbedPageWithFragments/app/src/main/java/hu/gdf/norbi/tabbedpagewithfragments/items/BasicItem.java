package hu.gdf.norbi.tabbedpagewithfragments.items;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class BasicItem {
   String name, description;

    public BasicItem() {
        this.name=this.description="unknow";
    }

    public BasicItem(String name) {
        this.name = name;
        this.description="unknow";
    }

    public BasicItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BasicItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
