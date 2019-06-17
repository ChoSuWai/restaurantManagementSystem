package admin;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.Database;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import model.Item;

public class AdminController implements Initializable{

	@FXML
	private JFXButton btnManageInfo;
	@FXML
	private VBox itemVBox; 
	@FXML
    private JFXTextField itemIdField;	
	@FXML
    private JFXTextField itemNameField;
	@FXML
	private ChoiceBox<String> choiceBox;
	@FXML
    private Button btnAddCategoryBox;
	@FXML
	private JFXTextField priceField;	 
	@FXML
	private JFXButton btnAddItem;
	@FXML
	private JFXButton btnEditItem;
    @FXML
    private JFXButton btnDeleteItem;
    @FXML
    private JFXButton btnViewItems;
    @FXML
    private VBox categoryVBox;
    @FXML
    private JFXTextField categoryField;
    @FXML
    private JFXButton btnAddNewCategory;
	 
    @FXML
    private Tab viewItemsTab;
    @FXML
    private TableView<Item> itemsTableView;
    @FXML
    private TableColumn<Item, Integer> idColumn;
    @FXML
    private TableColumn<Item, String> itemColumn;
    @FXML
    private TableColumn<Item, String> categoryColumn;
    @FXML
    private TableColumn<Item, Double> priceColumn;
    
	
	@FXML
    private TextField accIdField;
	@FXML
    private TextField accNameField;
	@FXML
	private PasswordField passField;
    @FXML
    private TextField confirmField;
    @FXML
    private ChoiceBox<String> roleField;
    @FXML
    private Button addAccBtn;
    @FXML
    private Button editAccBtn;
    @FXML
    private Button deleteAccBtn; 
    @FXML
    private Button viewAccBtn;
    
    @FXML
    private AnchorPane adminPane;
    @FXML
    private Button btnBack;
    @FXML
    private StackPane stackPane;
    
    @FXML
    private TextField oldIdField;
    @FXML
    private JFXButton btnProcess;
 

    @FXML
    private JFXButton btnViewReports;
 
    private Database db;
    public static int count;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		db=new Database();
		categoryVBox.setVisible(false);
		initData();
		loadItemsList();
		//choiceBox.getItems().clear();
		loadChoiceBoxData();
		loadRoleBoxInAddAcc();
		
	}
    
	private void initData() {
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
	
	}
	private void loadItemsList() {
		ObservableList<Item> itemsList=FXCollections.observableArrayList();
		
		ResultSet results=db.getAllItems();
		if(results!=null) {
			try {
				while(results.next()) {
					int id=results.getInt("item_id");
					String name=results.getString("item_name");
					String category=results.getString("category");
					String priceStr=results.getString("price");
					double price=Double.valueOf(priceStr);
					itemsList.add(new Item(id,name,category,price));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		itemsTableView.getItems().clear();
		itemsTableView.getItems().setAll(itemsList);
		
	}

    private void loadChoiceBoxData() {
		ResultSet results=db.getCategory();
		if(results!=null) {
			try {
				while(results.next()) {
					String category=results.getString("category");
					choiceBox.getItems().add(category);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			choiceBox.getSelectionModel().select(0);
		}	
	}
    
    private void loadRoleBoxInAddAcc() {
		roleField.getItems().add("Admin");
		roleField.getItems().add("Cashier");
		roleField.getSelectionModel().select(1);
	}

    
    @FXML
    void handleManageInfo(ActionEvent event) {

    }

    @FXML
    void handleViewReports(ActionEvent event) {

    }
    
    @FXML
    void btnAddCategoryBoxAction(ActionEvent event) {
    	categoryVBox.setVisible(true);
    }

    @FXML
    void handleAddItem(ActionEvent event) {
    	String idStr=itemIdField.getText();
    	String name=itemNameField.getText();
    	String category=choiceBox.getValue();
    	String priceStr=priceField.getText();
    	
    	boolean alreadyExist=false;
    	if(idStr.isEmpty()|| name.isEmpty()|| category==null|| priceStr.isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Please, fill all fields!", "Adding Item Operation", JOptionPane.WARNING_MESSAGE);
    		return;
    	}else {
    		ResultSet results=db.getAllItems();
    		if(results!=null) {
    			try {
					while(results.next()) {		    		
						int existId=results.getInt("item_id");
						String existName=results.getString("item_name");
						if(Integer.valueOf(idStr)==existId || name.equals(existName)) {
							alreadyExist=true;
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		//Check again.....
    		if(alreadyExist==true) {
    			JOptionPane.showMessageDialog(null, "This Account(Id or Name) has already Existed.\nView ItemsList!", "Adding Item Operation", JOptionPane.WARNING_MESSAGE);
    		}else {
    			int id=Integer.parseInt(idStr);
    			double price=Double.valueOf(priceStr);
    			db.addItem(id,name,category,price);
//    			if(ok) {
    				JOptionPane.showMessageDialog(null, "Adding Item Completd.", "Adding Item Operation", JOptionPane.INFORMATION_MESSAGE);
    				clearItemFields();
 //   			}//else {
//    				JOptionPane.showMessageDialog(null, "Adding Item Failed.", "Adding Item Operation", JOptionPane.ERROR_MESSAGE);
//    			}
    		}
    	}
    	
    }
    private void clearItemFields() {
		itemIdField.clear();
		itemNameField.clear();
		priceField.clear();
		
	}
    
    @FXML
    void handleAddNewCategory(ActionEvent event) {
    	String newCategory=categoryField.getText();
    	if(newCategory.isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Please, Write a Category!", "Adding Category Operation", JOptionPane.WARNING_MESSAGE);
    	}else {
    		if(db.addCategory(newCategory)) {
    			choiceBox.getItems().clear();
    			loadChoiceBoxData();
    			JOptionPane.showMessageDialog(null, "Adding Category Completd.", "Adding Category Operation", JOptionPane.INFORMATION_MESSAGE);
    			
			}else {
				JOptionPane.showMessageDialog(null, "Adding Category Failed.", "Adding Category Operation", JOptionPane.ERROR_MESSAGE);
			}
    		categoryVBox.setVisible(false);
    	}
    	
    }

//    @FXML
//    void itemIdFieldAction(ActionEvent event) throws NumberFormatException, SQLException {	
//    }
    @FXML
    void handeBtnProcess(ActionEvent event) throws SQLException {
  	
		if(oldIdField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Write item_id you wanna edit/delete and Click Process", "Editing Item Operation", JOptionPane.WARNING_MESSAGE);
		
		}else {	
			String idStr=oldIdField.getText();
			int id=Integer.parseInt(idStr);
			ResultSet result=db.getItemById(id);
			if(result.next()) {
				String name=result.getString("item_name");
		    	String category=result.getString("category");
		    	String price=result.getString("price");
				
		    	itemIdField.setText(idStr);
				itemNameField.setText(name);
				choiceBox.getSelectionModel().select(category);
				priceField.setText(price);
			}else {
				JOptionPane.showMessageDialog(null, "This Item_id Doesn't Exist.", "Editing/Deleting Item Operation", JOptionPane.ERROR_MESSAGE);
			}
		}
    }

	@FXML
    void handeEditItem(ActionEvent event) throws SQLException {
		String idStr=itemIdField.getText();
		String name=itemNameField.getText();
    	String category=choiceBox.getValue();
    	String priceStr=priceField.getText();
		if(oldIdField.getText().isEmpty() || idStr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Write item_id you wanna edit and Click Process", "Editing Item Operation", JOptionPane.WARNING_MESSAGE);
		
		}else {	
			
			int id=Integer.parseInt(idStr);		
			double price=Double.valueOf(priceStr);
			if(idStr.isEmpty()|| name.isEmpty()|| category==null|| priceStr.isEmpty()) {
	    		JOptionPane.showMessageDialog(null, "Please, fill all fields!", "Editing Item Operation", JOptionPane.WARNING_MESSAGE);
	    		return;
	    	}else {
	    		db.updateItemData(id,name,category,price);    	
	    		JOptionPane.showMessageDialog(null, "Editing Item OK.", "Editing Item Operation", JOptionPane.INFORMATION_MESSAGE);
	    		clearItemFields();
	    	}
		}
	}
	
//	@FXML
//	    void handelViewItemsTab(ActionEvent event) {
//			//loadItemsList();
//			System.out.print("Changed...");
//	    }

    @FXML
    void handleDeleteItem(ActionEvent event) {
		String idStr=itemIdField.getText();
		
		if(oldIdField.getText().isEmpty() || idStr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Write item_id you wanna delete and Click Process", "Deleting Item Operation", JOptionPane.WARNING_MESSAGE);
		
		}else {	
			
			int id=Integer.parseInt(idStr);		
	    	db.deleteItemData(id);
	    	JOptionPane.showMessageDialog(null, "Deleting Item OK.", "Deleting Item Operation", JOptionPane.INFORMATION_MESSAGE);
	    	clearItemFields();
		}
    }
    
    @FXML
    void handleViewItems(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/admin/viewItems.fxml"));
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Items List!");
        stage.show();
    }

    @FXML
    void handleAddAccount(ActionEvent event) throws SQLException {
    	String idStr=accIdField.getText();
    	String name=accNameField.getText();
    	String pass=passField.getText();
    	String confirmPass=confirmField.getText();
    	String role=roleField.getValue();
    	
    	if(idStr.isEmpty()|| name.isEmpty()|| pass.isEmpty()|| confirmPass.isEmpty()|| role==null) {
    		JOptionPane.showMessageDialog(null, "Please, fill all fields!", "Adding Item Operation", JOptionPane.WARNING_MESSAGE);
    		return;
    	}else if(!pass.equals(confirmPass)){
    		
    	}else {
    		int id=Integer.valueOf(idStr);
    		ResultSet results=db.checkUser(name,pass,role);
    		results.next();
        	count=results.getInt("count");
        	if(count==1) {
        		JOptionPane.showMessageDialog(null, "This Account(Id) has already Existed.\nView AccountsList!", "Adding Account Operation", JOptionPane.WARNING_MESSAGE);
    		}else {
    				db.addAccount(id,name,pass,role);
    				JOptionPane.showMessageDialog(null, "Adding Account Completd.", "Adding Account Operation", JOptionPane.INFORMATION_MESSAGE);
    				clearAccountFields();
    			
    		}
    	}
    }
    private void clearAccountFields() {
  		accIdField.clear();
  		accNameField.clear();
  		passField.clear();
  		confirmField.clear();
  		
  	}

    @FXML
    void handleEditAccount(ActionEvent event) {
    	
    }

    @FXML
    void handleDeleteAccount(ActionEvent event) {

    }
    
    @FXML
    void handleViewAccount(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/admin/viewAccounts.fxml"));
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Accounts List!");
        stage.show();
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


}
