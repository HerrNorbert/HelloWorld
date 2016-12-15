package hu.gdf.norbi.tabbedpagewithfragments.items;

/**
 * Created by Norbi on 2016. 12. 13..
 */

public class WishItem extends BasicItem {
    private boolean gotIt;
//    private CheckBox cb;

    public WishItem() {
        this.gotIt = false;
//        this.cb=null;
    }

    public WishItem(String name) {
        super(name);
        this.gotIt = false;
//        this.cb=null;
    }

    public WishItem(String name, String description) {
        super(name, description);
        this.gotIt = false;
//        this.cb=null;
    }

    public boolean isGotIt() {
        return gotIt;
    }

    public void setGotIt(boolean gotIt) {
        this.gotIt = gotIt;
    }


}
