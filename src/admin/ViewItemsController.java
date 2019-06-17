package admin;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

public class ViewItemsController implements Initializable {
	
	    @FXML
	    private TableView<Item> itemsTableView;
	    
	    @FXML
	    private TableColumn<Item, Integer> idColumn;
	    
	    @FXML
	    private TableColumn<Item, String> itemColumn;
	    
	    @FXML
	    private TableColumn<Item, String> categoryColumn;
	    
	    @FXML
	    private TableColumn<Item, String> priceColumn;
	    
	    private Database db;

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			db=new Database();
			initData();
			loadData();
			
		}

		private void initData() {
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			itemColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
			
		}

		private void loadData() {
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
}
