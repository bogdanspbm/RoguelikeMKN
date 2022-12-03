package database.adapter.implementation;

import database.adapter.DatabaseAdapter;
import exceptions.DatabaseException;
import inventory.objects.ItemDescription;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParamsDatabaseAdapter extends DatabaseAdapter {

    public ParamsDatabaseAdapter() throws DatabaseException {
        super();
        tableName = "params";
    }

    public List<String> getParamsList() throws DatabaseException {

        List<String> res = new ArrayList<>();

        Connection connection = database.getConnection();

        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + tableName + ";";

            ResultSet resultSet = statement.executeQuery(query);
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                res.add(name);
            }

            return res;
        } catch (SQLException e) {
            throw new DatabaseException("Database Exception: " + e.toString());
        }
    }
}

