package hu.gdf.norbi.shoppingtime;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by Norbi on 2016. 11. 29..
 */

public class ItemListFragment extends ListFragment  {
    public static final String TAG = "ItemListFragment";

    // State
    private WishAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }
    @Override
    public void onStart() {
        super.onStart();

        // Adapter letrehozasa esfeltoltese nehany elemmel
        ArrayList<Item> WishList = new ArrayList<Item>();
        WishList.add(new Item("első item","első item leírása"));
        WishList.add(new Item("második item","második item leírása"));
        WishList.add(new Item("harmadik item","harmadik item leírása"));
        adapter = new WishAdapter(getActivity(), WishList);
        setListAdapter(adapter);

        registerForContextMenu(getListView());
    }
    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.equals(getListView())) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(((Item) getListAdapter().getItem(info.position)).getName());

            String[] menuItems = getResources().getStringArray(R.array.todomenu);

            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }
    @Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int menuItemIndex = item.getItemId();
		if (menuItemIndex == 0) {
			((TodoAdapter) getListAdapter()).deleteRow((Todo) getListAdapter().getItem(info.position));
			((TodoAdapter) getListAdapter()).notifyDataSetChanged();
		}
		return true;
	}*/
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Item selectedItem = (Item) getListAdapter().getItem(position);

        /*if (listener != null) {
            listener.onTodoSelected(selectedTodo);
        }*/
    }
    /*
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.listmenu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.itemCreateTodo) {
			TodoCreateFragment createFragment = new TodoCreateFragment();
			createFragment.setTargetFragment(this, 0);

			FragmentManager fm = getFragmentManager();
			createFragment.show(fm, TodoCreateFragment.TAG);
		}

		return super.onOptionsItemSelected(item);
	}
     */
    public void onItemCreated(Item newItem) {
        adapter.addItem(newItem);
        adapter.notifyDataSetChanged();
    }
}
