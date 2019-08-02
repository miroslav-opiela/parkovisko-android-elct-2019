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

public class CallLogActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView listView;

    private SimpleCursorAdapter adapter;

    private static final int LOADER_ID = 11;
    private static final int PERMISSION_REQUEST_CODE = 54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED){
            // mam povolenie
            init();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG)) {
                // zobrazujem info o povoleni
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            }
        }
    }

    private void init() {
        getLoaderManager().initLoader(LOADER_ID, null, this);

        listView = findViewById(R.id.listView);
        String[] from = {CallLog.Calls.NUMBER};
        int[] to = {android.R.id.text1};
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null, from, to, 0);
        listView.setAdapter(adapter);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        if (id == LOADER_ID) {
            CursorLoader cursorLoader = new CursorLoader(this);
            cursorLoader.setUri(CallLog.Calls.CONTENT_URI);
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
