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
				// TODO Auto-generated catch block
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
	    }
	    
	    public void createDatabase() throws SQLException{
	        String createDb  = "Create Database if not exists restaurantdb";
	        stmt = conn.createStatement();
	        stmt.execute(createDb);
	    }
	    
	    public void creatTables() throws SQLException{
	    	
	        String createAcc = "Create Table if not exists account (acc_id int primary key,name varchar(40),password varchar(40),role varchar(40))";        
	       
	        String createCategory = "Create Table if not exists category (category varchar(40)primary key)";    
//	        String sql3 = "create table if not exists category (id int primary key,type varchar(20),item_id int,quantity int,sign varchar(40),remark varchar(255),transaction_date date,foreign key (item_id) references items(id))";

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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return false;
	    }
	    
	    public ResultSet itemsByCategory(String category) {
			String query="Select * From items Where category=?";
			try {
	    		preStmt=conn.prepareStatement(query);
	    		preStmt.setString(1, category);
	    		return preStmt.executeQuery();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	    public Connection getConnection(){
	        return conn;
	    }
	    
}
