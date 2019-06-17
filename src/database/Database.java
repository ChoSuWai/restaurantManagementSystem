package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	 private static String url = "jdbc:postgresql://localhost:5432/restaurantdb";
	    private static String user = "postgres";
	    private static String password = "4444asdf";
	    
	    private Connection conn;
	    
	    private static Database db;
	    
	    private Statement stmt;
	    private PreparedStatement preStmt;
	    
	    public Database(){
	    	//createDatabase();
	        try {
				 createConnection();
				 creatTables();
				 
//				 addCategory("Breakfast");
//				 addCategory("Lunch");
//				 addCategory("Dinner");
//				 addItem(1, "Noodle", "Breakfast", 200);
//				 addItem(2, "Rice", "Breakfast", 100);
//				 addItem(3, "Spicy", "Lunch", 200);
//				 addItem(4, "Lotteria", "Lunch", 100);
//				 addItem(5, "Pepsi", "Lunch", 100);
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}               
	    }
	    
	    public static Database getInstance() throws SQLException{
	        if(db==null){
	            db = new Database();
	        }
	        return db;
	    }
	    
	    public void createConnection() throws SQLException{
	        conn = DriverManager.getConnection(url,user,password);
	        //System.out.println("ConnectionDB Ok");
	    }
	    
	    public void createDatabase() throws SQLException{
	        String createDb  = "Create Database if not exists restaurantdb";
	        stmt = conn.createStatement();
	        stmt.execute(createDb);
	        System.out.println("Creating database Ok");
	    }
	    
	    public void creatTables() throws SQLException{
	    	
	        String createAcc = "Create Table if not exists account (acc_id int primary key,name varchar(40),password varchar(40),role varchar(40))";        
	       
	        String createCategory = "Create Table if not exists category (category varchar(40)primary key)";    
	        
	        String createItems = "Create Table if not exists items (item_id int primary key,item_name varchar(40),category varchar(40),price varchar(40))";            	      
	        String createSoldItems = "Create Table if not exists soldItems (name varchar(40),quantity int,price varchar(40))";    
	        
	        stmt = conn.createStatement();
	        stmt.execute(createAcc);   
	        stmt.execute(createCategory);
	        stmt.execute(createItems);
	        stmt.execute(createSoldItems);
	        ResultSet result=checkUser("cashier","123","Cashier");
	        if(!result.next()) {
	        	String createUser="Insert Into account (acc_id,name,password,role) Values (?,?,?,?)"; 	        	
	        	preStmt=conn.prepareStatement(createUser);
	        	preStmt.setInt(1, 2);
	        	preStmt.setString(2,"cashier");
	        	preStmt.setString(3,"123");
	        	preStmt.setString(4,"Cashier");
	        	preStmt.execute();      	
	        	System.out.println("First User Created.This is done only Once at the beginning of software running");
	        	
	        }
	        
	    }
	    public ResultSet checkUser(String name, String password, String role) {
			String checkUserSql="Select Count(*) As count From account Where name=? And password=? And role=?";
			try {

	    		preStmt=conn.prepareStatement(checkUserSql);
	    		preStmt.setString(1, name);
	    		preStmt.setString(2, password);
	    		preStmt.setString(3, role);
	    		return preStmt.executeQuery();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
//	    public ResultSet checkUserWithId(int id) {
//			String checkUserSql="Select Count(*) As count From account Where id=?";
//			try {
//
//	    		preStmt=conn.prepareStatement(checkUserSql);
//	    		preStmt.setInt(1, id);
////	    		preStmt.setString(2, name);
////	    		preStmt.setString(3, password);
////	    		preStmt.setString(4, role);
//	    		return preStmt.executeQuery();
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
	    
	    public boolean addAccount(int id, String name, String pass, String role) {
	    	String query="Insert Into account (acc_id,name,password,role) Values (?,?,?,?)";
	    	try {
	    		preStmt=conn.prepareStatement(query);
	    		preStmt.setInt(1, id);
	    		preStmt.setString(2, name);
	    		preStmt.setString(3, pass);
	    		preStmt.setString(4, role);
	    		return preStmt.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
	    public ResultSet getAllAccounts() {
			String query="Select * From account";
			try {
	    		stmt=conn.createStatement();
	    		return stmt.executeQuery(query);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

		//need to Check and Change
	    public boolean addCategory(String newCategory) {
	    	String query="Insert Into category(category) Values(?)";
	    	try {
	    		preStmt=conn.prepareStatement(query);
	    		preStmt.setString(1, newCategory);
	    		preStmt.execute();
		    	return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
	    }
	    
	    public ResultSet getCategory() {
	    	String query="Select * From category";
	    	
	    	try {
				stmt=conn.createStatement();
				return stmt.executeQuery(query);  	
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;  	
	    	
	    }
	    //need to Check
	    public boolean addItem(int id,String name,String category,double price) {
	    	String query="Insert Into items(item_id,item_name,category,price) Values(?,?,?,?)";
	    	try {
	    		preStmt=conn.prepareStatement(query);
	    		preStmt.setInt(1, id);
	    		preStmt.setString(2, name);
	    		preStmt.setString(3, category);
	    		preStmt.setString(4, String.valueOf(price));
	    		return preStmt.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	return false;
	    }
	    
	    public ResultSet getAllItems() {
			String query="Select * From items";
			try {
	    		stmt=conn.createStatement();
	    		return stmt.executeQuery(query);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	    
	    public ResultSet itemsByCategory(String category) {
			String query="Select * From items Where category=?";
			try {
	    		preStmt=conn.prepareStatement(query);
	    		preStmt.setString(1, category);
	    		return preStmt.executeQuery();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	    
	    public ResultSet getItemName(String itemName) {
	    	String query="Select * From items Where item_name=?";
			try {
	    		preStmt=conn.prepareStatement(query);
	    		preStmt.setString(1, itemName);
	    		return preStmt.executeQuery();		
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	     
	    public void setSoldItem(String name, int quantity, double price) {
			String query="Insert Into soldItems(name,quantity,price) Values(?,?,?)";
			try {
	    		preStmt=conn.prepareStatement(query);
	    		preStmt.setString(1, name);
	    		preStmt.setInt(2, quantity);
	    		preStmt.setString(3, String.valueOf(price));
	    		preStmt.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	    
	    public Connection getConnection(){
	        return conn;
	    }
	    
}
