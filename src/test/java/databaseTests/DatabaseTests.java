package databaseTests;

import database.adapter.implementation.ItemDatabaseAdapter;
import org.junit.Test;

import static database.Database.getDatabase;

public class DatabaseTests {

    @Test
    public void databaseConnection() {
        try {
            getDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Test
    public void simpleSelect(){
        try {
            ItemDatabaseAdapter adapter = new ItemDatabaseAdapter();
            adapter.getItemByID(1);
        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }

}
