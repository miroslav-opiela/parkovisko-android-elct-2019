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

/**
 * Aktivita so zoznamom companies zobrazenych v RecyclerView ulozenych v databaze
 */
public class CompaniesActivity extends AppCompatActivity {

    /**
     * ViewModel udrziava data potrebne pre aktivitu, resp. recyclerView
     */
    private CompanyViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);

        RecyclerView r = findViewById(R.id.recyclerview);
        // adapter pre recycler view - uchovava zoznam dat (nie live data)
        final CompanyListAdapter adapter = new CompanyListAdapter(this);
        r.setAdapter(adapter);
        r.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(CompanyViewModel.class);
        // datam vo viewModel sa nastavi pozorovatel
        viewModel.getData().observe(this, new Observer<List<Company>>() {
            @Override
            public void onChanged(List<Company> companies) {
                // pri zmene v datach vo viewModeli sa upravia aj adapter data
                adapter.setData(companies);
            }
        });
    }
}
