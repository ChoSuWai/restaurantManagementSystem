package main;

import java.sql.SQLException;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	 @Override
	    public void start(Stage stage) throws Exception {
		 
		 
		 try {
	            Database db = Database.getInstance();
	        } catch (SQLException e) {
	        	e.printStackTrace();
	            System.out.println("Cannot connect to database.Plz check the server configuration.");
	        }
		 	
	        Parent root = FXMLLoader.load(getClass().getResource("/main/login.fxml"));
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setTitle("Restaurant Management System");
	        stage.setMaximized(true);
	        stage.show();
	    }

	    
	    public static void main(String[] args) {
	        launch(args);
	    } 

}
