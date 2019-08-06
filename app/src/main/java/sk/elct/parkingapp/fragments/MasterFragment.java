package sk.elct.parkingapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import sk.elct.parkingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterFragment extends Fragment {

    private OnPhoneNumberClickListener listener;

    public MasterFragment() {
        // Required empty public constructor
    }

    public void setListener(OnPhoneNumberClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master, container, false);
        ListView listView = view.findViewById(R.id.listViewMaster);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String number = (String) adapterView.getAdapter().getItem(i);
                // upozornim listenera, cize aktivitu
                listener.onPhoneNumberClick(number);
            }
        });

        return view;
    }

}
