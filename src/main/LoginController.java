
package main;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import admin.AdminController;
import cashier.CashierController;
import dao.accountDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
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
    void handleLoginOperation(ActionEvent event) throws SQLException{
    	
    	accountDAO accDAO=new accountDAO();
    	String[] accList=accDAO.getAccount();
    	
    	String tmpName=accList[0];
    	String password=accList[1];
    	String role=accList[2];
//    	String tmpName="cashier";
//    	String password="123";
//    	String role="Cashier";
    	
    	if(nameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Please, fill all fields!", "Login Operation", JOptionPane.WARNING_MESSAGE);
    		
    	}else if(nameField.getText().equals(tmpName) && passwordField.getText().equals(password)) {
    		RadioButton selectedRole=(RadioButton) rdoRole.getSelectedToggle();
    		JOptionPane.showMessageDialog(null, selectedRole.getText(), "Login Operation", JOptionPane.WARNING_MESSAGE);
    		//if(rdoRole.getSelectedToggle()==rdoCashier) {
    		if(selectedRole==rdoCashier && selectedRole.getText().equals(role)) {	
    			CashierController cashier=new CashierController();
    	        Stage oldStage=(Stage) loginBtn.getScene().getWindow();
    	        try {
    				cashier.start(oldStage);
    				
    			} catch (IOException e) {
    				e.printStackTrace();
    				
    			}
    	        
    		}else if(selectedRole==rdoAdmin && selectedRole.getText().equals(role)){
    			//JOptionPane.showMessageDialog(null, selectedRole.getText()+" AdminView", "Login Operation", JOptionPane.WARNING_MESSAGE);
    			AdminController admin=new AdminController();
    			Stage oldStage=(Stage) loginBtn.getScene().getWindow();
    			try {
    				admin.start(oldStage);
				
    			} catch (IOException e) {
    				e.printStackTrace();
				
    			}

    		}else {
    			JOptionPane.showMessageDialog(null, "Something was wrong!", "Login Operation", JOptionPane.WARNING_MESSAGE);
    			
    		}
    		
	        
    	}else {               
    		JOptionPane.showMessageDialog(null, "Your name and password are wrong!", "Login Operation", JOptionPane.WARNING_MESSAGE);
    	
    	}
	        
    }


    @FXML
    void handleCancelOperation(ActionEvent event) {
    	nameField.clear();
    	passwordField.clear();
    	nameField.requestFocus();
    	
    }
    

}

