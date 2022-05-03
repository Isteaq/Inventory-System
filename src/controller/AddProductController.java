/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;


/**
 * FXML Controller class
 *
 * @author istea
 */

/**
 This class is used to Add Products and associated Parts. 
 */
public class AddProductController implements Initializable {
    Product addProduct = new Product(1,"Wrench",10.99,10,5,1);
    
    Stage stage;
    Parent scene; 


    @FXML
    private TextField addProductIdTxt;
    
    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TableView<Part> addProductTableViewBottom;

    @FXML
    private TableView<Part> addProductTableViewTop;

    @FXML
    private TableColumn<Product, Integer> partsIdColBottom;

    @FXML
    private TableColumn<Part, Integer> partsIdColTop;

    @FXML
    private TableColumn<Product, Integer> partsInvColBottom;

    @FXML
    private TableColumn<Part, Integer> partsInvColTop;

    @FXML
    private TableColumn<Product, String> partsNameColBottom;

    @FXML
    private TableColumn<Part, String> partsNameColTop;

    @FXML
    private TableColumn<Product, Double> partsPriceColBottom;

    @FXML
    private TableColumn<Part, Double> partsPriceColTop;
    
    /**
     * This method searches for a Product or ID. 
     * Searches are conducted using Strings and Integers. 
     * @param event The search Text field above Product. 
     */
    @FXML
    void onActionProductSearch(ActionEvent event) {
        String name = addProductSearchTxt.getText();
        ObservableList<Part> p = Inventory.lookupPart(name);
        addProductTableViewTop.setItems(p);
        
        try {
              
            int id = Integer.parseInt((addProductSearchTxt.getText()));
            addProductTableViewTop.getSelectionModel().select((MainFormController.selectPart(id)));
        }
        catch (NumberFormatException e){
        
            //Catch the NumberFormat Exception and ignore it 
        }     
    }
    
    /**
     * This method add Parts in the Add Product Screen. 
     * The transfer of data happens between the top table view and the bottom table view.
     * @param event The Add button located in the Add Product form. 
     */
    @FXML
    void onActionAddPart(ActionEvent event) {      
        Part selectedPart = addProductTableViewTop.getSelectionModel().getSelectedItem();
        
        
        addProduct.addAssociatedPart(selectedPart);
        addProductTableViewBottom.setItems(addProduct.getAllAssociatedParts());
  
    }

    
    /**
     * This method returns the user back to the Main Form without saving inputted data.
     * Inputs into the Text Fields and Parts added are not saved. 
     * @param event The Cancel button located on the Add Product screen.
     */
    @FXML
    void onActionCancelProduct(ActionEvent event) throws IOException {
       stage = (Stage)((Button)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
    }

    /**
     * This method removes the associatedPart. 
     * The part must be selected to be removed. 
     * @param event The Removed Associated Part button on the Add Product screen. 
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will remove the part. Do you want to continue? ");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
        Part selectedPart = addProductTableViewBottom.getSelectionModel().getSelectedItem();
        addProduct.deleteAssociatedPart(selectedPart);
 
    }
    }
    
    /** This method saves the inputted data and tests for logical errors.
     * The inputted data is saved to a Part object. The logical error this method test for is the inventory must not be be more than the maximum number of parts 
     * or minimum number of parts. The minimum must be less than the maximum. The maximum must be less than the minimum.  
     @param event The Save Button.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        
        int count = 1; 
        int countBottomTableParts = 0;
        String name = null;
        Integer stock = null;
        Double price = null;
        Integer max = null;
        Integer min = null; 
        
        for(Product product : Inventory.getAllProducts()){
            count++;
        }
      
        try
        {
        int id = count;
        name = addProductNameTxt.getText();
        stock = Integer.parseInt(addProductInvTxt.getText());
        price = Double.parseDouble(addProductPriceTxt.getText());
        max = Integer.parseInt(addProductMaxTxt.getText());
        min = Integer.parseInt(addProductMinTxt.getText());
        
        if((min < max) && (stock < max) && (stock > min)){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

       Inventory.addProduct(new Product(id, name,price, stock, min, max));
       
        //Add the associated parts to the Product that was just created
        for(Part p :addProductTableViewBottom.getItems()){
            countBottomTableParts++; 
            Inventory.getAllProducts().get(id-1).addAssociatedPart(p);
        }
      
        }
       
         else{
           
           if(!(min < max)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Min is not less than max");
            alert.showAndWait();
           }
           else if(!(stock < max)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Inventory cannot be greater than maxium");
            alert.showAndWait();
           }
           
           else if(!(stock > min)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Inventory cannot be less than minimum");
            alert.showAndWait();
           }
       }
        
        
        }
        catch(NumberFormatException e){
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();

        }
       
    }
    
    /**
     * Initializes the controller class.
     * Initializes parts to the top table view and bottom table view. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Parts TableView Main Form 
        addProductTableViewTop.setItems(Inventory.getAllParts());
        partsIdColTop.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameColTop.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvColTop.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceColTop.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partsIdColBottom.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameColBottom.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvColBottom.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceColBottom.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
    
}
