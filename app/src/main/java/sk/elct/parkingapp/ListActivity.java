package sk.elct.parkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.List;

import sk.elct.parkingapp.database.Company;
import sk.elct.parkingapp.database.CompanyListAdapter;
import sk.elct.parkingapp.database.CompanyViewModel;
import sk.elct.parkingapp.fragments.PhoneNumbersActivity;
import sk.elct.parkingapp.parking.CompanyTicket;
import sk.elct.parkingapp.parking.ParkingLot;
import sk.elct.parkingapp.parking.Ticket;

/**
 * Hlavna aktivita, ktora zobrazuje zoznam ticketov
 */
public class ListActivity extends AppCompatActivity {

    /**
     * List view uchovava jednotlive tickety
     */
    private ListView listView;

    /**
     * Pristup k datam cez controller parkingLot.
     */
    private ParkingLot parkingLot;

    /**
     * REQUEST CODE potrebny na identifikaciu intentu ked sa spusta aktivita s ocakavanym vysledkom
     */
    private static final int NEW_TICKET_REQUEST_CODE = 8;

    /**
     * Status kod ak vysledok prichadzajuci z inej aktivity je ok a obsahuje ticket
     */
    public static final int RESULT_OK_TICKET = 1;

    /**
     * Status kod ak vysledok prichadzajuci z inej aktivity je ok a obsahuje company ticket
     */
    public static final int RESULT_OK_COMPANY_TICKET = 2;

    private CompanyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        // po kliknuti na floating action button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent identifikuje aktivitu, ktora bude spustena
                Intent intent
                        = new Intent(ListActivity.this,
                        NewTicketActivity.class);
                // spusta sa ina aktivita a caka sa na vysledok
                startActivityForResult(intent, NEW_TICKET_REQUEST_CODE);
            }
        });

        // problem pri otacani zariadenia, treba ulozit do bundle
        Log.d("PARKING", "ON CREATE vytvara sa novy parking lot");

        // subor v internej pamati na ukladanie dat
        File file = new File(getFilesDir(),"parking.txt");
        // instancia controllera, data si nacitava zo suboru
        parkingLot = new ParkingLot(10, file);
        //parkingLot.demoData();


        RecyclerView r = findViewById(R.id.recyclerview);
        final CompanyListAdapter adapter = new CompanyListAdapter(this);
        r.setAdapter(adapter);
        r.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(CompanyViewModel.class);
        viewModel.getData().observe(this, new Observer<List<Company>>() {
            @Override
            public void onChanged(List<Company> companies) {
                adapter.setData(companies);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    /**
     * Vytvori menu podla zadaneho xml, jednotlive polozky su umiestnene
     * podla velkosti obrazovky bud priamo v app bare alebo pod kebab menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_activity_menu, menu);
        return true;
    }

    /**
     * Akcia vykonana po kliknuti na menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.itemOpenCallLog) {
            Intent intent = new Intent(this, CallLogActivity.class);
            startActivity(intent);
        }
        if (itemId == R.id.itemOpenPhoneNumbers) {
            Intent intent = new Intent(this, PhoneNumbersActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
