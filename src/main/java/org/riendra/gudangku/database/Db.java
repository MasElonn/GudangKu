package org.riendra.gudangku.database;

import org.riendra.gudangku.models.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {
    static String url = "jdbc:sqlite:items.db";
    public static Boolean isConnected = false;
    private Connection connection;
    public void connect(){

        try {
            if(connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url);
                System.out.println("Connection to SQLite has been established.");
                isConnected = true;
                createNewTable();
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    private void createNewTable() {
        connect();

        String query = """
                CREATE TABLE IF NOT EXISTS items (
                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                 name TEXT NOT NULL,
                 category TEXT,
                 quantity INTEGER NOT NULL,
                 price REAL,
                 created_at TEXT DEFAULT CURRENT_TIMESTAMP
                );""";


        try (Connection conn = DriverManager.getConnection(url);
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Table created.");
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public void insertItem(String name, String category, int quantity, double price){
        connect();
        String query = """
                INSERT INTO items (name, category, quantity,price) VALUES(?,?,?,?)
                """;
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,name);
            statement.setString(2,category);
            statement.setInt(3,quantity);
            statement.setDouble(4,price);
            statement.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();

        }
    }
    public void deleteItem(int id){
        connect();
        String query = """
                DELETE FROM items WHERE id = ?
                """;
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, id);

            statement.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();

        }

    }
    public void updateItem(int id, String name, String category, int quantity, double price){
        connect();
        String query = """
                UPDATE items SET name = ?, category = ?, quantity = ?, price = ? WHERE id = ?
                """;
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,name);
            statement.setString(2,category);
            statement.setInt(3,quantity);
            statement.setDouble(4,price);
            statement.setInt(5,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
