package sk.elct.parkingapp;

import android.Manifest;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

/**
 * Aktivita zobrazuje zoznam volani.
 */
public class CallLogActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView listView;

    /**
     * Adapter pre listview. Tentokrat nie zalozeny na poli/liste ale na cursore.
     */
    private SimpleCursorAdapter adapter;

    /**
     * ID pre loader, ktory nacitava data z databazy.
     */
    private static final int LOADER_ID = 11;
    /**
     * Kod pre permission na citanie calllog.
     */
    private static final int PERMISSION_REQUEST_CODE = 54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

        // zisti ci su udelene povolenia
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED){
            // mam povolenie
            init();
        } else {
            // ak uz povolenie bolo raz odmietnute, treba pouzivatelovi vysvetlit poziadavku
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG)) {
                // zobrazujem info o povoleni

                // snackbar sa nezobrazuje idealne, neda sa na neho klikat, treba zmenit prvy parameter na nieco lepsie
            /*    Snackbar.make(getWindow().getDecorView().getRootView(), "Potrebujem povolenie na zoznam cisel", Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(CallLogActivity.this,
                                        new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSION_REQUEST_CODE);
                            }
                        }).show();*/
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSION_REQUEST_CODE);
            } else {
                // ziadam o povolenie (prvykrat)
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSION_REQUEST_CODE);
            }
        }

    }

    /**
     * Vola sa v pripade udelenia nejakeho povolenia.
     */
        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            }
        }
    }

    /**
     * Vola sa v metode onCreate ak boli udelene prislusne povolenia.
     */
    private void init() {
        // loader, ktory bude nacitavat data z databazy, callback je this,
        // teda objekt tejto triedy reaguje na vysledok nacitavania v 3 metodach
        // onCreateLoader, onLoadFinished a onLoaderReset
        getLoaderManager().initLoader(LOADER_ID, null, this);

        listView = findViewById(R.id.listView);
        // data sa beru z tychto stlpcov (v tomto pripade iba 1 stlpec s tel. cislami)
        String[] from = {CallLog.Calls.NUMBER};
        // data sa zobrazuju do prislusneho textview
        int[] to = {android.R.id.text1};
        // adapter zatial ma cursor null, po nacitani cez loader sa to zmeni
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null, from, to, 0);
        listView.setAdapter(adapter);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        // identifikuje sa prislusny loader
        if (id == LOADER_ID) {
            // vytvori sa loader na cursor
            CursorLoader cursorLoader = new CursorLoader(this);
            // definuje sa ktora tabulka v databaze sa bude nacitavat
            cursorLoader.setUri(CallLog.Calls.CONTENT_URI);
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // ak sa data nacitali, zmeni sa cursor v adapteri
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // ak sa nacitavanie resetuje, data v adapteri sa tiez resetuju
        adapter.swapCursor(null);
    }
}
