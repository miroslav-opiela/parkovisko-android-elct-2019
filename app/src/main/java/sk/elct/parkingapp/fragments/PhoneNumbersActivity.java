package sk.elct.parkingapp.fragments;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import sk.elct.parkingapp.R;

/**
 * Aktivita skladajuca sa z 2 fragmentov.
 * Implementuje interface OnPhoneNumberClickListener
 * - teda reaguje na udalost kliknutia na tel. cislo v zozname v MasterFragment
 */
public class PhoneNumbersActivity extends FragmentActivity
        implements OnPhoneNumberClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_numbers);


        if (isTabletMode()) {
            // v rezime velkej obrazovky (tablet) uz mame k dispozicii fragment definovany v layout xml
            MasterFragment masterFragment =
                    (MasterFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.masterFragment);
            masterFragment.setListener(this);
        } else {
            // v pripade dynamickeho nasadzovania fragmentov na malej obrazovke, treba vyrobit MasterFragment
            MasterFragment masterFragment = new MasterFragment();
            masterFragment.setListener(this);

            // transakcia na vlozenie MasterFragment na obrazovku
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.phoneNumbersActivity, masterFragment)
                    .commit();
        }
    }

    /**
     * Tablet rezim oznacuje velke displeje. Nastava ak layout nema prislusne ID
     */
    private boolean isTabletMode() {
        return findViewById(R.id.phoneNumbersActivity) == null;
    }

    /**
     * Metoda sa vola ked sa kliklo na tel. cislo. Metodu spusta masterFragment
     */
    @Override
    public void onPhoneNumberClick(String phoneNumber) {
        // toast
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();

        if (isTabletMode()) {
            // v tablet rezime si vytiahneme z layoutu prislusny detailFragment
            DetailFragment detailFragment
                    = (DetailFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            detailFragment.setContent(phoneNumber);
        } else {
            // v dynamickom rezime je potrebne vyrobit detail fragment
            DetailFragment detailFragment = DetailFragment.newDetailFragment(phoneNumber);
            // transakcia na vlozenie detailfragmentu.
            // AddToBackStack zaruci spravne spravanie pri stlaceni sipky spat.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.phoneNumbersActivity, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
