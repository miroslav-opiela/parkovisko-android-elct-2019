package sk.elct.parkingapp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * ViewModel uchovava data potrebne pre pouzivatelske rozhranie
 */
public class CompanyViewModel extends AndroidViewModel {

    /**
     * Repository na pristup k ulozenym datam
     */
    private CompaniesRepository repository;
    /**
     * Samotne data ulozene v LiveData = vie sa na nich prihlasit posluchac - observer,
     * ktory reaguje na kazdu zmenu v tychto datach
     */
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
