package sk.elct.parkingapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CompaniesRepository {

    private CompanyDAO companyDAO;
    private LiveData<List<Company>> data;

    public CompaniesRepository(Application application) {
        CompaniesDatabase database = CompaniesDatabase.getDatabase(application);
        companyDAO = database.companyDao();
        data = companyDAO.getAllCompanies();
    }

    public LiveData<List<Company>> getData() {
        return data;
    }

    public void insert(Company company) {
        new InsertAsyncTask(companyDAO).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Company, Void, Void> {

        private CompanyDAO dao;

        public InsertAsyncTask(CompanyDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Company... companies) {
            dao.insert(companies[0]);
            return null;
        }

    }

}
