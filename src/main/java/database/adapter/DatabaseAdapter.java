package database.adapter;

import database.Database;
import exceptions.DatabaseException;

public class DatabaseAdapter {

    protected Database database;
    protected String tableName = "";

    public DatabaseAdapter() throws DatabaseException {
        this.database = Database.getDatabase();
    }

}
