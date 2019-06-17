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
import model.Account;

public class ViewAccountsController implements Initializable {

	@FXML
    private TableView<Account> accountsTableView;
    
    @FXML
    private TableColumn<Account, Integer> idColumn;
    
    @FXML
    private TableColumn<Account, String> accNameColumn;
    
    @FXML
    private TableColumn<Account, String> passwordColumn;
    
    @FXML
    private TableColumn<Account, String> roleColumn;
    
    private Database db;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		db=new Database();
		initData();
		loadData();
		
	}

	private void initData() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		accNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
		
	}

	private void loadData() {
		ObservableList<Account> accountsList=FXCollections.observableArrayList();
		
		ResultSet results=db.getAllAccounts();
		if(results!=null) {
			try {
				while(results.next()) {
					int id=results.getInt("acc_id");
					String name=results.getString("name");
					String password=results.getString("password");
					String role=results.getString("role");
					accountsList.add(new Account(id, name, password, role));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		accountsTableView.getItems().clear();
		accountsTableView.getItems().setAll(accountsList);
		
	}
}
