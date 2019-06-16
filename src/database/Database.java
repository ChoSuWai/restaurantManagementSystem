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
				 
				 addCategory("Breakfast");
				 addCategory("Lunch");
				 addCategory("Dinner");
				 addItem(1, "Noodle", "Breakfast", 200);
				 addItem(2, "Rice", "Breakfast", 100);
				 addItem(3, "Spicy", "Lunch", 200);
				 addItem(4, "Lotteria", "Lunch", 100);
				 addItem(5, "Pepsi", "Lunch", 100);
				 
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
	        String createDb  = "create database if not exists restaurantdb";
	        stmt = conn.createStatement();
	        stmt.execute(createDb);
	    }
	    
	    public void creatTables() throws SQLException{
	    	
	        String createAcc = "create table if not exists account (acc_id int primary key,name varchar(40),password varchar(40),role varchar(40))";        
	        String insertSql="insert into account (acc_id,name,password,role) values (2,'admin','123','Cashier')"; 
	        String createCategory = "create table if not exists category (category varchar(40)primary key)";    
//	        String sql3 = "create table if not exists category (id int primary key,type varchar(20),item_id int,quantity int,sign varchar(40),remark varchar(255),transaction_date date,foreign key (item_id) references items(id))";
<<<<<<< HEAD
//	        Statement stmt3 = conn.createStatement();
//	        stmt3.execute(sql3);
//        
//	        String sql2 = "create table if not exists items (id int primary key,name varchar(40),category varchar(40),price int)";
//	        Statement stmt2 = conn.createStatement();
//	        stmt2.execute(sql2);
=======
	        String createItems = "create table if not exists items (item_id int primary key,item_name varchar(40),category varchar(40),price varchar(40))";            	      
	        String createSoldItems = "create table if not exists soldItems (name varchar(40),quantity int,price varchar(40))";    
	        
	        stmt = conn.createStatement();
	        stmt.execute(createAcc);   
	        stmt.execute(insertSql);
	        stmt.execute(createCategory);
	        stmt.execute(createItems);
	        stmt.execute(createSoldItems);
>>>>>>> master
	        
	    }
	    //need to Check and Change
	    public boolean addCategory(String newCategory) {
	    	String query="insert into category(category) values(?)";
	    	//String sql2="insert into category(category) values('Lunch')";
	    	try {
	    		preStmt=conn.prepareStatement(query);
	    		preStmt.setString(1, newCategory);
	    		preStmt.execute();
				//stmt=conn.createStatement();
				//stmt.execute(sql1);
		    	//stmt.execute(sql2);
		    	return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	    }
	    
	    public ResultSet getCategory() {
	    	String query="select * from category";
	    	
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
	    	String query="insert into items(item_id,item_name,category,price) values(?,?,?,?)";
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
			String query="select * from items where category=?";
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
	    	String query="select * from items where item_name=?";
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
			String query="insert into soldItems(name,quantity,price) values(?,?,?)";
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
