/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.manager;

import database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
//import ui.main.MainController;
import util.Message;

/**
 * FXML Controller class
 *
 * @author shweyee
 */
public class ManagerController implements Initializable {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private Button addItemBtn;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Button addBtn;
    @FXML
    private VBox categoryBox;
    @FXML
    private TextField categoryField;
    @FXML
    private Button categoryBtn;
    private DatabaseHandler db;
    @FXML
    private Button viewItemsBtn;
    @FXML
    private Button itemBtn;
    @FXML
    private Button accountBtn;
    @FXML
    private StackPane centerPane;
    @FXML
    private AnchorPane managerPane;
    @FXML
    private VBox itemVBox;
    @FXML
    private Button existBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DatabaseHandler();
        categoryBox.setVisible(false);
        choiceBox.getItems().clear();
        loadChoiceBoxData();
    }

    @FXML
    private void handleAddItem(ActionEvent event) {
        String id = idField.getText();
        String name = nameField.getText();
        String category = choiceBox.getValue();
        String value = priceField.getText();

        if (id.isEmpty() || name.isEmpty() || category == null || value.isEmpty()) {
            Message.showErrorMessage("Error", "Pleaese,fill all fields.");
            return;
        }
        Optional<ButtonType> result = Message.showConfirmMessage("Add Item", "Are you sure you want to add this item");
        if (result.get() == ButtonType.OK) {
            int price = Integer.parseInt(value);
            if (db.addItem(id, name, category, price)) {
                Message.showMessage("Information", "Adding item completed...");
                closeFields();
            } else {
                Message.showErrorMessage("Error", "Adding item failed...");
            }
        }
    }

    private void closeFields() {
        idField.clear();
        nameField.clear();
        choiceBox.setValue(null);
        priceField.clear();
    }

    @FXML
    private void HandleAddCategory(ActionEvent event) {
        categoryBox.setVisible(true);
        categoryField.clear();
    }

    @FXML
    private void AddCategoryAction(ActionEvent event) {
        String addedCategory = categoryField.getText();
        if (addedCategory.isEmpty()) {
            Message.showErrorMessage("Error", "Please,write a category.");
            return;
        }
        Optional<ButtonType> result = Message.showConfirmMessage("Add Category", "Are you sure you want to add this category");
        if (result.get() == ButtonType.OK) {
            //    choiceBox.getItems().add(addedCategory);
            if (db.addCategory(addedCategory)) {
                choiceBox.getItems().clear();
                loadChoiceBoxData();
                Message.showMessage("Information", "Adding category completed...");
            } else {
                Message.showErrorMessage("Error", "Adding category failed...");
            }
        }
        categoryBox.setVisible(false);
    }

    private void loadChoiceBoxData() {
        ResultSet results = db.getCategory();
        if (results != null) {
            try {
                while (results.next()) {
                    String category = results.getString("category");
                    choiceBox.getItems().add(category);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void HandleViewItems(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/viewItems/viewItems.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(managerPane.getScene().getWindow());
        stage.show();
    }

    @FXML
    private void startItemBtnAction(ActionEvent event) {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(itemVBox);
    }

    @FXML
    private void startAccountBtnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/createAccount/account.fxml"));
        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);
    }

//    @FXML
//    private void startExistBtnAction(ActionEvent event) throws IOException {
//        MainController.count = 0;//*******************
//        Parent root = FXMLLoader.load(getClass().getResource("/ui/main/main.fxml"));
//        managerPane.getChildren().clear();
//        managerPane.getChildren().add(root);
////        Scene scene =new Scene(root);
////        Stage oldStage=(Stage) choiceBox.getScene().getWindow();
////        oldStage.close();
////        Stage stage=new Stage();
////        stage.setScene(scene);
////        stage.show();
//    }
}
