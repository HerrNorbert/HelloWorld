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

import hu.gdf.norbi.tabbedpagewithfragments.items.CartItem;
import hu.gdf.norbi.tabbedpagewithfragments.items.WishItem;

public class MainActivity extends AppCompatActivity {
    ////////////////////////////////////////NFC
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private NfcAdapter mNfcAdapter;
    private static String readedNFC;

    ////////////////////////////////////////////////NFC
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * ho.gdf.norbi.fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private hu.gdf.norbi.tabbedpagewithfragments.adapters.SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readedNFC = "";
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new hu.gdf.norbi.tabbedpagewithfragments.adapters.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*//gomb ami nem kell
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        //NFC///////////////////////////
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
    //        Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }

        if (!mNfcAdapter.isEnabled()) {
  //          Toast.makeText(this, "NFC is disabled.", Toast.LENGTH_LONG).show();
        } else {
//            Toast.makeText(this, "NFC is enabled", Toast.LENGTH_LONG).show();
        }

        handleIntent(getIntent());
        /////////////////////////
        mViewPager.setCurrentItem(1);
    }

    public NfcAdapter getMyNFCadpter() {
        return mNfcAdapter;
    }

    /////////////////////////////////////////////NFC
    @Override
    protected void onResume() {
        super.onResume();
      //  mViewPager.setCurrentItem(1);
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
        //////////////////////////////////////////////
       /* CartFragment cf = new CartFragment();
        cf.onCreate(new Bundle());
        cf.onResume();
        if(getReadedNFC()!=""){
            int id = Integer.parseInt(getReadedNFC());
            if(cf.isCorrectID(id))
                cf.AddItem(id);
            clearReadedNFC();
        }*/
        //////////////////////////////////////////////
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
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

            // In case we would still use the Tech Discovered Intent
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

    ///////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void writeToFile(hu.gdf.norbi.tabbedpagewithfragments.adapters.ItemAdapter adapter,Context context, Boolean isCartItem) {
        final String CART = "cart.csv";
        final String WISH = "wish.csv";
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
        final String CART = "cart.csv";
        final String WISH = "wish.csv";
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
}