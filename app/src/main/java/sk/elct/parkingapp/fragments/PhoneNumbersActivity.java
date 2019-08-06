package sk.elct.parkingapp.fragments;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import sk.elct.parkingapp.R;

public class PhoneNumbersActivity extends FragmentActivity
        implements OnPhoneNumberClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_numbers);


        if (isTabletMode()) {
            MasterFragment masterFragment =
                    (MasterFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.masterFragment);
            masterFragment.setListener(this);
        } else {
            MasterFragment masterFragment = new MasterFragment();
            masterFragment.setListener(this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.phoneNumbersActivity, masterFragment)
                    .commit();
        }
    }

    private boolean isTabletMode() {
        return findViewById(R.id.phoneNumbersActivity) == null;
    }

    @Override
    public void onPhoneNumberClick(String phoneNumber) {
        // toast
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();

        if (isTabletMode()) {
            DetailFragment detailFragment
                    = (DetailFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            detailFragment.setContent(phoneNumber);
        } else {
            DetailFragment detailFragment = DetailFragment.newDetailFragment(phoneNumber);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.phoneNumbersActivity, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
