package admin;

import java.awt.Label;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;

public class AdminController implements Initializable{

	@FXML
    private TextField idField;

    @FXML
    private ChoiceBox<?> categoryBox;

    @FXML
    private JFXButton btnAddItem;

    @FXML
    private ChoiceBox<?> roleField;

    @FXML
    private TextField confirmField;

    @FXML
    private TextField nameField;

    @FXML
    private VBox billPane;

    @FXML
    private VBox billPane1;

    @FXML
    private Button editIAccBtn;

    @FXML
    private JFXTextField categoryField;

    @FXML
    private PasswordField passField;

    @FXML
    private Label itemName;

    @FXML
    private Button btnAddCategoryBox;

    @FXML
    private JFXTextField priceField;

    @FXML
    private Button viewIAccBtn;

    @FXML
    private AnchorPane adminPane;

    @FXML
    private Label addAccount;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private Button btnBack;

    @FXML
    private Button deleteAccBtn;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> itemColumn;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private Button addIAccBtn;

    @FXML
    private JFXButton btnAddCategory;

    @FXML
    private JFXButton btnEditItem;

    @FXML
    private JFXButton btnDeleteItem;

    @FXML
    private TableColumn<?, ?> idColumn;

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
    void handleAddAccount(ActionEvent event) {

    }

    @FXML
    void handleViewAccount(ActionEvent event) {

    }

    @FXML
    void handleEditAccount(ActionEvent event) {

    }

    @FXML
    void handleDeleteAccount(ActionEvent event) {

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
