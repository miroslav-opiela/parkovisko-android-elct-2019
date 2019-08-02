package sk.elct.parkingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import sk.elct.parkingapp.parking.CompanyTicket;
import sk.elct.parkingapp.parking.Ticket;


public class NewTicketActivity extends AppCompatActivity {

    private EditText editTextEcv;
    private EditText editTextCompany;
    private CheckBox checkBoxCompany;

    public static final String EXTRA_NAME_TICKET = "ticket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEcv = findViewById(R.id.editTextEcv);
        editTextCompany = findViewById(R.id.editTextCompany);
        checkBoxCompany = findViewById(R.id.checkBoxCompany);

        checkBoxCompany.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                editTextCompany.setEnabled(checked);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_ticket_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.itemAddTicket) {
            Ticket ticket = createTicket();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_NAME_TICKET, ticket);
            if (ticket != null) {
                setResult(Activity.RESULT_OK, resultIntent);
            } else {
                setResult(Activity.RESULT_CANCELED, resultIntent);
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Ticket createTicket() {
        String ecv = editTextEcv.getText().toString();
        if (ecv.length() == 0) {
            Toast.makeText(this, "Insert ECV", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (checkBoxCompany.isChecked()) {
            String company = editTextCompany.getText().toString();
            if (company.length() == 0) {
                Toast.makeText(this, "Insert Company Name", Toast.LENGTH_SHORT).show();
                return null;
            }
            return new CompanyTicket(ecv, company);
        } else {
            return new Ticket(ecv);
        }
    }

}
