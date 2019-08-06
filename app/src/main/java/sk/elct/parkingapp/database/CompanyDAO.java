package sk.elct.parkingapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CompanyDAO {

    @Insert
    void insert(Company company);

    @Query("SELECT * FROM companies")
    LiveData<List<Company>> getAllCompanies();

    @Query("DELETE FROM companies")
    void deleteAll();

}
