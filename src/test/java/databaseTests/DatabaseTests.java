package databaseTests;

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

}
