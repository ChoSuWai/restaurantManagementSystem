package main;

import java.io.IOException;

import javax.swing.JOptionPane;

import cashier.CashierController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField nameField;
    
    @FXML
    private PasswordField passwordField;
    
	@FXML
    private RadioButton rdoAdmin;
    
    @FXML
    private RadioButton rdoCashier;
    
    @FXML
    private ToggleGroup rdoRole;
    
    @FXML
    private Button loginBtn;
    
    @FXML
    private Button cancelBtn;


    @FXML
    void handleLoginOperation(ActionEvent event){
    	
    	String tmpName="cashier";
    	String password="123";
    	String role="Cashier";
    	
    	if(nameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Please, fill all fields!", "Login Operation", JOptionPane.WARNING_MESSAGE);
    		
    	}else if(nameField.getText().equals(tmpName) && passwordField.getText().equals(password)) {
    		
    		if(rdoRole.getSelectedToggle()==rdoCashier) {
    			
    			CashierController cashier=new CashierController();
    	        Stage oldStage=(Stage) loginBtn.getScene().getWindow();
    	        try {
    				cashier.start(oldStage);
    				
    			} catch (IOException e) {
    				e.printStackTrace();
    				
    			}
    	        
    		}else {
    			JOptionPane.showMessageDialog(null, "You should select Cashier Role!", "Login Operation", JOptionPane.WARNING_MESSAGE);
    		}
    		
	        
    	}else {               
    		JOptionPane.showMessageDialog(null, "Your name and password are wrong!", "Login Operation", JOptionPane.WARNING_MESSAGE);
    	
    	}
<<<<<<< HEAD
=======
    	
    	
	
	        
>>>>>>> master
	        
    }


    @FXML
    void handleCancelOperation(ActionEvent event) {
    	nameField.clear();
    	passwordField.clear();
    	nameField.requestFocus();
    	
    }
    

}
