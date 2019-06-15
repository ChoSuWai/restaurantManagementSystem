/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shweyee
 */
public class DatabaseHandler {

    private String location = "jdbc:mysql://localhost:3306";
    private Connection conn = null;
    private Statement stmt;
    private PreparedStatement preStmt;

    public DatabaseHandler() {
        connect();
        createDatabase();
    }

    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(location, "root", "");
            System.out.println("Connection success...");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createDatabase() {
        String createDb = "CREATE DATABASE IF NOT EXISTS cafedb CHARACTER SET utf8 COLLATE utf8_general_ci";
        String createAccountTable = "CREATE TABLE IF NOT EXISTS cafedb.account (user_name varchar(20)PRIMARY KEY,full_name varchar(20),password varchar(20),role varchar(20))";
        String createUser = "INSERT INTO cafedb.account (user_name,full_name,password,role) VALUES (?,?,?,?)";
        String createItemsTable = "CREATE TABLE IF NOT EXISTS cafedb.items (item_id varchar(20)PRIMARY KEY,item_name varchar(20),category varchar(20),price int)";
        String createCategoryTable = "CREATE TABLE IF NOT EXISTS cafedb.category (category varchar(20)PRIMARY KEY)";
        String createSoldItemsTable = "CREATE TABLE IF NOT EXISTS cafedb.soldItems (name varchar(20),quantity int,price int,cookComplete boolean default false)";
        try {
            stmt = conn.createStatement();
            stmt.execute(createDb);
            stmt.execute(createAccountTable);
            stmt.execute(createItemsTable);
            stmt.execute(createCategoryTable);
            stmt.execute(createSoldItemsTable);
            ResultSet result = checkUser("admin", "admin", "Manager");//*****
            if (!result.next()) {
                preStmt = conn.prepareStatement(createUser);
                preStmt.setString(1, "admin");
                preStmt.setString(2, "Admin");
                preStmt.setString(3, "admin");
                preStmt.setString(4, "Manager");
                preStmt.execute();
            }
            System.out.println("Database created.");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet checkUser(String name, String password, String role) {
        String checkUserSql = "SELECT COUNT(*) AS count FROM cafedb.account WHERE user_name=? AND password=? AND role=?";

        try {
            preStmt = conn.prepareStatement(checkUserSql);
            preStmt.setString(1, name);
            preStmt.setString(2, password);
            preStmt.setString(3, role);
            return preStmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean addItem(String id, String name, String category, int price) {
        String insertSql = "INSERT INTO cafedb.items (item_id,item_name,category,price) VALUES (?,?,?,?)";
        try {
            preStmt = conn.prepareStatement(insertSql);
            preStmt.setString(1, id);
            preStmt.setString(2, name);
            preStmt.setString(3, category);
            preStmt.setInt(4, price);
            preStmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addCategory(String addedCategory) {
        String insertSql = "INSERT INTO cafedb.category (category) VALUES (?)";
        try {
            preStmt = conn.prepareStatement(insertSql);
            preStmt.setString(1, addedCategory);
            preStmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ResultSet getCategory() {
        String query = "SELECT * FROM cafedb.category";
        try {
            stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet getItems() {
        String query = "SELECT * FROM cafedb.items";
        try {
            stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean setAccount(String name, String fullName, String password, String role) {
        String insertSql = "INSERT INTO cafedb.account (user_name,full_name,password,role) VALUES (?,?,?,?)";
        try {
            preStmt = conn.prepareStatement(insertSql);
            preStmt.setString(1, name);
            preStmt.setString(2, fullName);
            preStmt.setString(3, password);
            preStmt.setString(4, role);
            preStmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ResultSet getAccounts() {
        String query = "SELECT * FROM cafedb.account";
        try {
            stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ResultSet itemByCategory(String category) {
        String query = "SELECT * FROM cafedb.items WHERE category=?";
        try {
            preStmt = conn.prepareStatement(query);
            preStmt.setString(1, category);
            return preStmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet getItemsByName(String itemName) {
        String query = "SELECT * FROM cafedb.items WHERE item_name=?";
        try {
            preStmt = conn.prepareStatement(query);
            preStmt.setString(1, itemName);
            return preStmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setSoldItem(String name, int quantity, int price) {
        String insertSql = "INSERT INTO cafedb.soldItems (name,quantity,price) VALUES (?,?,?)";
        try {
            preStmt = conn.prepareStatement(insertSql);
            preStmt.setString(1, name);
            preStmt.setInt(2, quantity);
            preStmt.setInt(3, price);
            preStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getsoldItemByNotCook() {
        String query = "SELECT * FROM cafedb.soldItems WHERE cookComplete=false";
        try {
            stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateCookComplete(String name) {
        String updateSql = "UPDATE cafedb.solditems SET cookComplete=true WHERE name=?";
        try {
            preStmt = conn.prepareStatement(updateSql);
            preStmt.setString(1, name);
            preStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
