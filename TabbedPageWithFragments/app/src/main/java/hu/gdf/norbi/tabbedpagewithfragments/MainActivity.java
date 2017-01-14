package hu.gdf.norbi.tabbedpagewithfragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import hu.gdf.norbi.tabbedpagewithfragments.fragments.CartFragment;
import hu.gdf.norbi.tabbedpagewithfragments.fragments.MainFragment;
import hu.gdf.norbi.tabbedpagewithfragments.fragments.WishListFragment;
import hu.gdf.norbi.tabbedpagewithfragments.items.CartItem;
import hu.gdf.norbi.tabbedpagewithfragments.items.WishItem;

public class MainActivity extends AppCompatActivity {
    ////////////////////////////////////////NFC
    private static final String CART = "cart.csv";
    private static final String WISH = "wish.csv";
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private NfcAdapter mNfcAdapter;
    private static String readedNFC;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readedNFC = "";
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            finish();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
  //          Toast.makeText(this, "NFC is disabled.", Toast.LENGTH_LONG).show();
        } else {
//            Toast.makeText(this, "NFC is enabled", Toast.LENGTH_LONG).show();
        }
        handleIntent(getIntent());
        mViewPager.setCurrentItem(1);
    }

    public NfcAdapter getMyNFCadpter() {
        return mNfcAdapter;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    public static void setReadedNFC(String s) {
        readedNFC = readedNFC + s + ";";
        Log.d("nfc readed", s);
    }

    public static void clearReadedNFC() {
        readedNFC = "";
    }

    public static String getReadedNFC() {
        return readedNFC;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void writeToFile(hu.gdf.norbi.tabbedpagewithfragments.adapters.ItemAdapter adapter,Context context, Boolean isCartItem) {
        String line;
        OutputStreamWriter outputStreamWriter = null;
        try {
            if(isCartItem) {
                outputStreamWriter = new OutputStreamWriter(context.openFileOutput(CART, Context.MODE_PRIVATE));
                outputStreamWriter.write("");
                for(int i=0;i<adapter.getItemCount();i++){
                    CartItem item = (CartItem) adapter.get_item(i);
                    line=item.getName()+';'+item.getDescription()+';'+item.getId()+';'+item.getPrize()+';'+item.getMount()+'\n';
                    outputStreamWriter.write(line);
                }
            }else{
                outputStreamWriter = new OutputStreamWriter(context.openFileOutput(WISH, Context.MODE_PRIVATE));
                outputStreamWriter.write("");
                for(int i=0;i<adapter.getItemCount();i++){
                    WishItem item = (WishItem) adapter.get_item(i);
                    line=item.getName()+';'+item.getDescription()+';'+item.getMount()+';'+item.isGotIt()+'\n';
                    outputStreamWriter.write(line);
                    Log.d("main",line);
                }
            }
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        Log.d("main","kiirtam");

    }
    public static void readFromFile(hu.gdf.norbi.tabbedpagewithfragments.adapters.ItemAdapter adapter,Context context, Boolean isCartItem) {
        String line = "";
        try {
            InputStream inputStream;
            if(isCartItem)
                inputStream = context.openFileInput(CART);
            else
                inputStream = context.openFileInput(WISH);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();

                if(isCartItem){
                    while ( (line = bufferedReader.readLine()) != null ) {
                        stringBuilder.append(line);
                        String[] currItem = line.split(";");
                        CartItem item = new CartItem(currItem[0],currItem[1],Integer.parseInt(currItem[2]),Integer.parseInt(currItem[3]));
                        adapter.add_item(item);
                    }
                }else{
                    while ( (line = bufferedReader.readLine()) != null ) {
                        stringBuilder.append(line);
                        String[] currItem = line.split(";");
                        WishItem item = new WishItem(currItem[0],currItem[1]);
                        item.setMount(Integer.parseInt(currItem[2]));
                        item.setGotIt(Boolean.parseBoolean(currItem[3]));
                        adapter.add_item(item);
                    }
                }
                inputStream.close();
                line = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        Log.d("main",line);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 : return new WishListFragment();
                case 1 : return new MainFragment();
                case 2 : return new CartFragment();
                default : return null;//new WishListFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.titleWishList);
                case 1:
                    return getResources().getString(R.string.titleMain);
                case 2:
                    return getResources().getString(R.string.titleCart);
            }
            return null;
        }
    }
}