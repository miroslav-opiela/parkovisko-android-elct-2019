package sk.elct.parkingapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import sk.elct.parkingapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newDetailFragment(String content) {
        DetailFragment detailFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("phone", content);

        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        String content = bundle.getString("phone");
        setContent(content);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public void setContent(String content) {
        TextView textView = getView().findViewById(R.id.textViewDetail);
        textView.setText(content);
    }

}
