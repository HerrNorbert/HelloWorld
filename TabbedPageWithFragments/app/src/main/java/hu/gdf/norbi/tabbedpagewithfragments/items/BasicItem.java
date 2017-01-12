package hu.gdf.norbi.tabbedpagewithfragments.items;

/**
 * Created by Norbi on 2016. 12. 05..
 */

public class BasicItem {
   protected String name, description;
   protected int mount;

    public BasicItem() {
        this.name=this.description="unknown";
        this.mount=1;
    }

    public BasicItem(String name) {
        this.name = name;
        this.description="unknown";
        this.mount=1;
    }

    public BasicItem(String name, String description) {
        this.name = name;
        this.description = description;
        this.mount=1;
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

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    public void addMount(){
        this.mount++;
    }

    public void reduceMount(){
        this.mount--;
    }

    @Override
    public String toString() {
        return "BasicItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mount=" + mount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicItem)) return false;

        BasicItem item = (BasicItem) o;

        if (!getName().equals(item.getName())) return false;
        return getDescription().equals(item.getDescription());

    }
}
