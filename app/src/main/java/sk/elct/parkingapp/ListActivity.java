package sk.elct.parkingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import sk.elct.parkingapp.parking.CompanyTicket;
import sk.elct.parkingapp.parking.ParkingLot;
import sk.elct.parkingapp.parking.Ticket;

public class ListActivity extends AppCompatActivity {

    private ListView listView;

    private ParkingLot parkingLot;

    private static final int NEW_TICKET_REQUEST_CODE = 8;
    public static final int RESULT_OK_TICKET = 1;
    public static final int RESULT_OK_COMPANY_TICKET = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(ListActivity.this,
                        NewTicketActivity.class);
                startActivityForResult(intent, NEW_TICKET_REQUEST_CODE);
            }
        });

        // problem pri otacani zariadenia, treba ulozit do bundle
        Log.d("PARKING", "ON CREATE vytvara sa novy parking lot");
        File file = new File(getFilesDir(),"parking.txt");
        parkingLot = new ParkingLot(10, file);
        //parkingLot.demoData();

        listView = findViewById(R.id.listViewTickets);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ListAdapter adapter = new ArrayAdapter<Ticket>(this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                parkingLot.getAllTickets()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                Ticket ticket = getItem(position);
                text1.setText(ticket.getEcv());
                if (ticket instanceof CompanyTicket) {
                    CompanyTicket companyTicket = (CompanyTicket) ticket;
                    text2.setText(companyTicket.getCompany());
                }
                return view;
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                Ticket t = (Ticket) adapterView.getAdapter().getItem(position);
                int sum = parkingLot.checkOut(t.getEcv());
                Log.d("TEST", "clicked on " + position);
                new CheckOutDialog(t, sum).show(getSupportFragmentManager(),
                        "checkoutDialog");
                // nejake riesenie na aktualizaciu stavu listview
                ((ArrayAdapter<Ticket>) adapter).remove(t);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
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
}
