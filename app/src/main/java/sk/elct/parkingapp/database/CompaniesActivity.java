package sk.elct.parkingapp.database;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sk.elct.parkingapp.R;

public class CompaniesActivity extends AppCompatActivity {

    private CompanyViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);

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
}
