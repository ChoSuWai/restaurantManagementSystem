package admin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;

public class AdminController implements Initializable{

	@FXML
    private JFXTextField idField;

    @FXML
    private JFXTextField categoryField;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnAddCategoryBox;
    
    @FXML
    private ChoiceBox<?> categoryBox;

    @FXML
    private JFXTextField priceField;

    @FXML
    private JFXButton btnAddCategory;

    @FXML
    private JFXButton btnAddItem;

    @FXML
    private JFXButton btnEditItem;

    @FXML
    private JFXTextField nameField;

    @FXML
    private AnchorPane adminPane;

    @FXML
    private JFXButton btnDeleteItem;

    @FXML
    void addCategoryAction(ActionEvent event) {

    }

    @FXML
    void handleAddItem(ActionEvent event) {

    }

    @FXML
    void handelEditItem(ActionEvent event) {

    }

    @FXML
    void handleDeleteItem(ActionEvent event) {

    }

    @FXML
    void handleAddCategory(ActionEvent event) {

    }
   
   
    @FXML
    void backToLoginForm(ActionEvent event) throws Exception {
    	
    	Main main=new Main();
        Stage oldStage=(Stage) btnBack.getScene().getWindow();
        oldStage.setMaximized(false);
        
        try {
        	
			main.start(oldStage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

	public void start(Stage newStage) throws IOException {

		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin.fxml"));
        
		  adminPane = loader.load();
	           
	      newStage.setTitle("Admin View!");
	      //newStage.setScene(new Scene(anchorPane, 2000, 700, Color.WHITESMOKE));
	      newStage.setScene(new Scene(adminPane));
	      newStage.show();
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
