package database.adapter.implementation;

import database.adapter.DatabaseAdapter;
import exceptions.DatabaseException;
import inventory.objects.ItemDescription;
import objects.animations.objects.Animation;
import structures.AnimationStructure;
import structures.Vector2DTimeline;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnimationDatabaseAdapter extends DatabaseAdapter {
    public AnimationDatabaseAdapter() throws DatabaseException {
        super();
        tableName = "animation";
    }

    public AnimationStructure getAnimationStructureByName(String name) throws DatabaseException {
        Connection connection = database.getConnection();

        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM " + tableName + " WHERE name = '" + name + "';";

            ResultSet resultSet = statement.executeQuery(query);
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                String texture = resultSet.getString("source");
                int sizeX = resultSet.getInt("size_x");
                int sizeY = resultSet.getInt("size_y");
                int timePerFrame = resultSet.getInt("time_per_frame");
                int frame_a = resultSet.getInt("frame_a");
                int frame_b = resultSet.getInt("frame_b");
                int frame_c = resultSet.getInt("frame_c");
                int frame_d = resultSet.getInt("frame_d");

                return new AnimationStructure(name, texture, new Dimension(sizeX, sizeY), new Vector2DTimeline(frame_a, frame_c, frame_b, frame_d), timePerFrame);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database Exception: " + e.toString());
        }
        throw new DatabaseException("Database Exception: \n Empty result: " + name);
    }
}

