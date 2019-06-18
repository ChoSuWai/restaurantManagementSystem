package cashier;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Main;
import model.Cashier;

public class CashierController implements Initializable {

	@FXML
    private AnchorPane cashierPane;
	
    @FXML
    private Label totalLabel;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Button editBtn;

    @FXML
    private VBox billPane;

    @FXML
    private TableView<Cashier> tableView;
    @FXML
    private TableColumn<Cashier, String> itemColumn;
    @FXML
    private TableColumn<Cashier, Integer> quantityColumn;
    @FXML
    private TableColumn<Cashier, Double> priceColumn;

    @FXML
    private TextField totalField;

    @FXML
    private ChoiceBox<String> choiceBox;   

    @FXML
    private Label restaurantName;

    @FXML
    private Button receiptBtn;
    
    @FXML
    private Button btnBack;
    
    
    private Database db;
    static ObservableList<Cashier> menulist;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	db=new Database();
    	addCategoryToChoiceBox();
    	loadMenuByCategory();
    	initData();//*****************
    	tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	menulist=FXCollections.observableArrayList();

    	choiceBox.getSelectionModel().selectedItemProperty().addListener((ob, oldVal, newVal) -> {//Lamda Expression
    			loadMenuByCategory();
    		}
    	);

    }

	private void initData() {
		itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		
	}

	private void addCategoryToChoiceBox() {
    	ObservableList<String> categories = FXCollections.observableArrayList();
   	 	
    	ResultSet results=db.getCategory();
    	if(results!=null) {
    		try {
    			while(results.next()) {
    				String category=results.getString("category");
    				categories.add(category);
    			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
   	 	choiceBox.getItems().addAll(categories);
   	 	choiceBox.getSelectionModel().select(categories.get(0));
		
	}
	
	 private void loadMenuByCategory() {
		ObservableList<String> list=FXCollections.observableArrayList();
		String category=choiceBox.getValue();
		
		ResultSet results=db.itemsByCategory(category);
		if(results !=null) {
			try {
    			while(results.next()) {
    				String name=results.getString("item_name");
    				list.add(name);
    			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

				flowPane.getChildren().clear();
	              for (String food : list) {
	                  Button button = new Button(food);
	                  button.setPrefSize(100, 70);
	                  button.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE,CornerRadii.EMPTY,Insets.EMPTY)));
	                  flowPane.getChildren().addAll(button);
	                  flowPane.setHgap(20);
	                  flowPane.setVgap(15);
	                  button.setOnAction(new EventHandler<ActionEvent>() {
	                      @Override
	                      public void handle(ActionEvent event) {
	                          String itemName = button.getText();
	                          if (menulist.size() != 0) {
	                              Cashier data = menulist.get(menulist.size() - 1);
	                              if (itemName.equals(data.getItem())) {
	                                  data.setPrice(data.getPrice() + (data.getPrice() / data.getQuantity()));
	                                  data.setQuantity(data.getQuantity() + 1);
	                                  tableView.getItems().clear();
	                                  tableView.getItems().addAll(menulist);
	
	                                  totalField.setText(calculateTotal() + " Ks");
	                                  return;
	                              }
	                          }
	                          menulist.add(getItem(itemName));
	                          totalField.setText(calculateTotal() + " Ks");
	                          tableView.getItems().clear();
	                          tableView.getItems().addAll(menulist);
	                      }
	                  });
	              }      			
	}
	 
	 private Cashier getItem(String itemName) {
			ResultSet result=db.getItemName(itemName);
			try {
				while(result.next()) {
					return new Cashier(itemName,1,Double.valueOf(result.getString("price")));
				}
				
			}catch(SQLException e){
				e.printStackTrace();
			}
			return null;
		}
	 
	 private double calculateTotal() {
			double total=0;
			for(Cashier cashier: menulist) {
				total+=cashier.getPrice();
			}
			return total;
		}

	public void start(Stage newStage) throws IOException {
		
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/cashier/cashier.fxml"));
          
		  cashierPane = loader.load();
	           
	      newStage.setTitle("Cashier View!");
	      newStage.setScene(new Scene(cashierPane));
	      newStage.show();
		
	}
	
	@FXML
    void handleEditBtn(ActionEvent event) {
		ObservableList<Cashier> selectedItems=tableView.getSelectionModel().getSelectedItems();
		menulist.removeAll(selectedItems);
		totalField.setText(calculateTotal()+" Ks");
		tableView.getItems().clear();
		tableView.getItems().setAll(menulist);
		
    }

    @FXML
    void handleReceiptBtn(ActionEvent event) {
    	for(Cashier cash:menulist) {
    		String name=cash.getItem();
    		int quantity=cash.getQuantity();
    		double price=cash.getPrice();
    		db.setSoldItem(name,quantity,price);
    	}
    	
    	PrinterJob job=PrinterJob.createPrinterJob();
    	if(job!=null && job.showPageSetupDialog(restaurantName.getScene().getWindow())) {
    		boolean success=job.printPage(billPane);
    		if(success) {
    			job.endJob();
    		}
    	}
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
    }


}
