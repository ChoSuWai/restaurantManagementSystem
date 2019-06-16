package cashier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;

public class CashierController implements Initializable {

	@FXML
    private AnchorPane cashierPane;
	
    @FXML
    private Label totalLabel;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Button editBtn;

    @FXML
    private VBox costPane;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TextField totalField;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TableColumn<?, ?> itemColumn;

    @FXML
    private Label cafeName;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private Button receiptBtn;
    
    @FXML
    private Button btnBack;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	 ObservableList<String> categories = FXCollections.observableArrayList();
    	 categories.addAll("Breakfast","Lunch","Dinner","Drinks","Desserts");
    	 
    	choiceBox.getItems().addAll(categories);
        choiceBox.getSelectionModel().select(0);
        
        String chosenItem=choiceBox.getSelectionModel().getSelectedItem();
        if(chosenItem=="Breakfast") {
        	
        	  String[] foodForBreakfast= {"Bread","Fried Rice","ShanNoodle","A","B","C","D","E","F","G"};
        	  flowPane.getChildren().clear();
              for (String food : foodForBreakfast) {
                  Button button = new Button(food);
                  button.setPrefSize(100, 70);
                  flowPane.getChildren().addAll(button);
                  flowPane.setHgap(20);
                  flowPane.setVgap(15);
//                  button.setOnAction(new EventHandler<ActionEvent>() {
//                      @Override
//                      public void handle(ActionEvent event) {
//                          String itemName = button.getText();
//                          if (menulist.size() != 0) {
//                              Cashier data = menulist.get(menulist.size() - 1);
//                              if (itemName.equals(data.getItem())) {
//                                  data.setPrice(data.getPrice() + (data.getPrice() / data.getQuantity()));
//                                  data.setQuantity(data.getQuantity() + 1);
//                                  tableView.getItems().clear();
//                                  tableView.getItems().addAll(menulist);
//
//                                  totalField.setText(calculateTotal() + " Ks");
//                                  return;
//                              }
//                          }
//                          menulist.add(getItem(itemName));
//                          totalField.setText(calculateTotal() + " Ks");
//                          tableView.getItems().clear();
//                          tableView.getItems().addAll(menulist);
//                      }
//                  });
              }
        	
        }
        
//        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                //System.out.println(observable);
//                System.out.println("Old Value : " + oldValue);
//                System.out.println("New Value : " + newValue);
//            }
//
//        });//Just an example
//        choiceBox.getSelectionModel().selectedItemProperty().addListener((ob, oldVal, newVal) -> {//Lamda Expression
//            //loadMenuByChoiceBox();
//        }
//        );

    }

    @FXML
    void handleEditBtn(ActionEvent event) {

    }

    @FXML
    void handleReceiptBtn(ActionEvent event) {

    }
    
    @FXML
    private void backToMain(ActionEvent event) throws Exception{
    
        Main main=new Main();
        Stage oldStage=(Stage) btnBack.getScene().getWindow();
        oldStage.setMaximized(false);
        
        try {
        	
			main.start(oldStage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
//    	 Parent root = FXMLLoader.load(getClass().getResource("/main/login.fxml"));
//         cashierPane.getChildren().clear();
//         cashierPane.getChildren().add(root);
    }
    
//    @FXML
//    void cboCategoriesOperation(ActionEvent event) {
////    	String chosenItem=choiceBox.getSelectionModel().getSelectedItem();
////
////    	if(chosenItem.equals("Breakfast")) {
////    		JOptionPane.showMessageDialog(null, "B", "", JOptionPane.INFORMATION_MESSAGE);
////    	}
//    	
//    	
//    }

	public void start(Stage newStage) throws IOException {
		
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/cashier/cashier.fxml"));
          
		  cashierPane = loader.load();
	           
	      newStage.setTitle("Cashier View!");
	      //newStage.setScene(new Scene(anchorPane, 2000, 700, Color.WHITESMOKE));
	      newStage.setScene(new Scene(cashierPane));
	      newStage.show();
		
	}

}
