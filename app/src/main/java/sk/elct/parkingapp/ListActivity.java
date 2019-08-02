package sk.elct.parkingapp;

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

import java.util.Arrays;
import java.util.List;

import sk.elct.parkingapp.parking.CompanyTicket;
import sk.elct.parkingapp.parking.ParkingLot;
import sk.elct.parkingapp.parking.Ticket;

public class ListActivity extends AppCompatActivity {

    private ListView listView;

    private ParkingLot parkingLot;

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
                startActivity(intent);
            }
        });

        parkingLot = new ParkingLot(10);
        parkingLot.demoData();

        listView = findViewById(R.id.listViewTickets);

        ListAdapter adapter = new ArrayAdapter<Ticket>(this,
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
                Log.d("TEST", "clicked on " + position);
            }
        });
    }

}
