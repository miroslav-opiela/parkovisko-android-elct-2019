package sk.elct.parkingapp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CompanyViewModel extends AndroidViewModel {

    private CompaniesRepository repository;
    private LiveData<List<Company>> data;

    public CompanyViewModel(@NonNull Application application) {
        super(application);
        repository = new CompaniesRepository(application);
        data = repository.getData();
    }

    public LiveData<List<Company>> getData() {
        return data;
    }

    public void insert(Company company) {
        repository.insert(company);
    }
}
