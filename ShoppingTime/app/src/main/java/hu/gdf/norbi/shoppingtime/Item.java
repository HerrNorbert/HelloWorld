package hu.gdf.norbi.shoppingtime;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Norbi on 2016. 11. 28..
 */
//basic class for items
public class Item implements Parcelable {
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

    public Item(Parcel in)
    {
        Prize = in.readInt();
        Name = in.readString();
        Description = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Prize);
        parcel.writeString(Name);
        parcel.writeString(Description);
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
