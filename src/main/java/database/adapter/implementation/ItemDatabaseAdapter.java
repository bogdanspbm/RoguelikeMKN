package database.adapter.implementation;

import database.adapter.DatabaseAdapter;
import exceptions.DatabaseException;
import inventory.objects.ItemDescription;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemDatabaseAdapter extends DatabaseAdapter {
    public ItemDatabaseAdapter() throws DatabaseException {
        super();
        tableName = "items";
    }

    public ItemDescription getItemByID(int id) throws DatabaseException {
        Connection connection = database.getConnection();

        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + tableName + " WHERE id = " + id + ";";

            ResultSet resultSet = statement.executeQuery(query);
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int sizeX = resultSet.getInt("size_x");
                int sizeY = resultSet.getInt("size_y");
                int stackSize = resultSet.getInt("stack_size");

                return new ItemDescription(id, stackSize, sizeX, sizeY, name);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database Exception: " + e.toString());
        }
        throw new DatabaseException("Database Exception: \n Empty result");
    }
}
