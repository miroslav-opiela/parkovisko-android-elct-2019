package sk.elct.parkingapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// ENTITA company
// anotacie oznacuju nazov tabulky v databaze a jednotlive premenne v zmysle databazovych stlpcov (atributov)
// robi sa ORM (object relation mapping) - teda ako suvisi jeden objekt s jednym riadkom v tabulke

@Entity(tableName = "companies")
public class Company {

    // stlpec name je zaroven primarny kluc
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
