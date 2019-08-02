package sk.elct.parkingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import sk.elct.parkingapp.parking.Ticket;

public class CheckOutDialog extends DialogFragment {

    private Ticket ticket;
    private double sum;

    public CheckOutDialog(Ticket ticket, int sum) {
        this.ticket = ticket;
        this.sum = sum / 100.0;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity());
        builder.setMessage("zaplat " + sum + "EUR")
                .setTitle("CHECK OUT " + ticket.getEcv())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // ak parking lot iba vypocital sumu, tak tu odhlasi ticket
                        // ak je checkout urobeny, tak tu sa vypise nejaka hlaska
                    }
                });
        return builder.create();
    }
}
