package sk.elct.parkingapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Company.class}, version = 1)
public abstract class CompaniesDatabase extends RoomDatabase {

    public abstract CompanyDAO companyDao();

    private static volatile CompaniesDatabase INSTANCE;

    static CompaniesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CompaniesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CompaniesDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CompanyDAO mDao;

        PopulateDbAsync(CompaniesDatabase db) {
            mDao = db.companyDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Company c = new Company();
            c.setName("ELCT");
            mDao.insert(c);
            Company d = new Company();
            d.setName("TESCO");
            mDao.insert(d);
            return null;
        }
    }
}
