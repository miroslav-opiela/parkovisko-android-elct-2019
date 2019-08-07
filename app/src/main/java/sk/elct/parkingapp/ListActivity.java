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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import sk.elct.parkingapp.database.CompaniesActivity;
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

        listView = findViewById(R.id.listViewTickets);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // adapter popisuje data v listview ziskane z listu/pola
        final ListAdapter adapter = new ArrayAdapter<Ticket>(this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                parkingLot.getAllTickets()) {

            /**
             * Prekryta metoda, ktora definuje ako sa jednotlivym komponentom
             * (textview) priradia konkretne casti dat
             */
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                Ticket ticket = getItem(position);
                text1.setText(ticket.getEcv());
                // ak ide o company ticket, nastavuje sa aj druhy riadok
                if (ticket instanceof CompanyTicket) {
                    CompanyTicket companyTicket = (CompanyTicket) ticket;
                    text2.setText(companyTicket.getCompany());
                }
                return view;
            }
        };
        listView.setAdapter(adapter);

        // anonymna trieda implementujuca interface s metodou onItemClick
        // teda vola sa vzdy ked sa klikne na nejaku polozku v list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                Ticket t = (Ticket) adapterView.getAdapter().getItem(position);
                int sum = parkingLot.checkOut(t.getEcv());
                Log.d("TEST", "clicked on " + position);
                // vytvara sa novy dialog fragment (je to v samostatnej triede)
                new CheckOutDialog(t, sum).show(getSupportFragmentManager(),
                        "checkoutDialog");
                // odstrani polozku aj z adaptera, checkout odstranuje iba z DAO
                ((ArrayAdapter<Ticket>) adapter).remove(t);
            }
        });
    }

    /**
     * Metoda sa vola, ked aktivita ktoru sme volali cez startActivityForResult skonci
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        // overime request a result kody a podla toho vykoname akciu, data sa vytahuju z intentu
        if (requestCode == NEW_TICKET_REQUEST_CODE) {
            if (resultCode == RESULT_OK_TICKET) {
                Ticket ticket = (Ticket) data.getSerializableExtra(NewTicketActivity.EXTRA_NAME_TICKET);
                parkingLot.checkIn(ticket.getEcv(), null);
            }
            if (resultCode == RESULT_OK_COMPANY_TICKET) {
                CompanyTicket ticket = (CompanyTicket) data.getSerializableExtra(NewTicketActivity.EXTRA_NAME_TICKET);
                parkingLot.checkIn(ticket.getEcv(), ticket.getCompany());
            }
        }
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
        if (itemId == R.id.itemDatabaseActivity) {
            Intent intent = new Intent(this, CompaniesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
