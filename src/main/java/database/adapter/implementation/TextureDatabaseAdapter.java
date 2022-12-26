package database.adapter.implementation;

import database.adapter.DatabaseAdapter;
import exceptions.DatabaseException;
import inventory.objects.ItemDescription;
import objects.animations.objects.TextureSource;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import static singleton.ImageBuffer.getImageFromBuffer;

public class TextureDatabaseAdapter extends DatabaseAdapter {

    HashMap<String, TextureSource> sources = new HashMap<>();

    public TextureDatabaseAdapter() throws DatabaseException {
        super();
        tableName = "textures";
    }

    public TextureSource getTextureByName(String name) {

        if (sources.containsKey(name)) {
            return sources.get(name);
        }

        Connection connection = database.getConnection();

        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + tableName + " WHERE name = '" + name + "';";

            ResultSet resultSet = statement.executeQuery(query);
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                String path = resultSet.getString("path");
                sources.put(name, getImageFromBuffer(path));
                return sources.get(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
