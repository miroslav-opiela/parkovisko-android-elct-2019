package sk.elct.parkingapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// interface CompanyDAO definuje ako sa bude pristupovat k datam - ktore metody CRUD budu existovat
// + prislusne SQL dopyty

// triedu implementujucu tento interface nevyrvarame, Android to vytvara podla anotacii

@Dao
public interface CompanyDAO {

    @Insert
    void insert(Company company);

    @Query("SELECT * FROM companies")
    LiveData<List<Company>> getAllCompanies();

    @Query("DELETE FROM companies")
    void deleteAll();

}
