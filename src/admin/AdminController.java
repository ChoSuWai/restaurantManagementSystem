package admin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class AdminController implements Initializable{

	 @FXML
	    private TextField idField;

	    @FXML
	    private VBox categoryBox;

	    @FXML
	    private VBox itemVBox;

	    @FXML
	    private TextField nameField;

	    @FXML
	    private Button itemBtn;

	    @FXML
	    private Button addBtn;

	    @FXML
	    private Button viewItemsBtn;

	    @FXML
	    private TextField categoryField;

	    @FXML
	    private Button accountBtn;

	    @FXML
	    private TextField priceField;

	    @FXML
	    private Button categoryBtn;

	    @FXML
	    private Button addItemBtn;

	    @FXML
	    private ChoiceBox<?> choiceBox;

	    @FXML
	    private AnchorPane adminPane;

	    @FXML
	    private Button existBtn;

	    @FXML
	    private StackPane centerPane;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
    @FXML
    void HandleAddCategory(ActionEvent event) {

    }

    @FXML
    void handleAddItem(ActionEvent event) {

    }

    @FXML
    void HandleViewItems(ActionEvent event) {

    }

    @FXML
    void AddCategoryAction(ActionEvent event) {

    }

    @FXML
    void startItemBtnAction(ActionEvent event) {

    }

    @FXML
    void startAccountBtnAction(ActionEvent event) {

    }

    @FXML
    void startExistBtnAction(ActionEvent event) {

    }

	public void start(Stage newStage) throws IOException {

		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin.fxml"));
        
		  adminPane = loader.load();
	           
	      newStage.setTitle("Admin View!");
	      //newStage.setScene(new Scene(anchorPane, 2000, 700, Color.WHITESMOKE));
	      newStage.setScene(new Scene(adminPane));
	      newStage.show();
		
	}

}
