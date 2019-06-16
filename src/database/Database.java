package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	 private static String url = "jdbc:postgresql://localhost:5432/restaurantdb";
	    private static String user = "postgres";
	    private static String password = "222068";
	    
	    private Connection conn;
	    
	    private static Database db;
	    
	    
	    private Database() throws SQLException{
	        createConnection();
	        //createDatabase();
	        creatTables();
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
	        String sql  = "create database if not exists restaurantdb";
	        Statement stmt = conn.createStatement();
	        stmt.execute(sql);
	    }
	    
	    public void creatTables() throws SQLException{
	    	
	        String sql1 = "create table if not exists account (id int primary key,name varchar(40),password varchar(40),role varchar(40))";
	        String insertSql="insert into account (id,name,password,role) values (1,'admin','123','Cashier')"; 
	        Statement stmt1 = conn.createStatement();
	        stmt1.execute(sql1);
	        stmt1.execute(insertSql);
	        
	        String sql2 = "create table if not exists items (id int primary key,name varchar(40),price int,stock int)";
	        Statement stmt2 = conn.createStatement();
	        stmt2.execute(sql2);
//	        
//	        String sql3 = "create table if not exists transactions (id int primary key auto_increment,type varchar(20),item_id int,quantity int,sign varchar(40),remark varchar(255),transaction_date date,foreign key (item_id) references items(id))";
//	        Statement stmt3 = conn.createStatement();
//	        stmt3.execute(sql3);
	    }
	     
	    public Connection getConnection(){
	        return conn;
	    }
	    
}
