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
import model.Product;
import model.Part;

/**
 * FXML Controller class
 *
 * @author istea
 */

/**
 This class is used to Modify a selected Product and it's associated Parts. 
 */
public class ModifyProductFormController implements Initializable {
    private static Product transferedProductFromMain = null; 

    Stage stage;
    Parent scene; 
    
    @FXML
    private TableColumn<Product, Integer> modifyProductInvBottomCol;

    @FXML
    private TableColumn<Product, Integer> modifyProductInvTopCol;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TableColumn<Product, String> modifyProductPartNameBottomCol;

    @FXML
    private TableColumn<Product, String> modifyProductPartNameTopCol;

    @FXML
    private TableColumn<Product, Integer> modifyProductPartsIdBottomCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartsIdTopCol;

    @FXML
    private TableColumn<Product, Double> modifyProductPriceBottomCol;

    @FXML
    private TableColumn<Product, Double> modifyProductPriceTopCol;
    
    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TextField modifyProductSearchTxt;

    @FXML
    private TableView<Part> modifyProductTableViewBottom;

    @FXML
    private TableView<Part> modifyProductTableViewTop;
    
    
    
    /** This method is used to transfer Product data from the Main form to the Modify Product screen.
     * It uses the variable selectedProductFromMain and transferedProductFromMain as link between the two controllers. 
     * @param selectedProductFromMain Retrieves the selected Product Data from the Main Form.  
     */
    
    public static void setProductFields(Product selectedProductFromMain) {
        ModifyProductFormController.transferedProductFromMain = selectedProductFromMain;
    }

    /** This method is used filter and search for Parts on the Modify Part Screen.
     *  Strings and Integer are used as inputs. 
     * @param event The Search field above the top table. 
     */
    @FXML
    void onActionModifyProductSearch(ActionEvent event) {
        String name = modifyProductSearchTxt.getText();
        ObservableList<Part> p = Inventory.lookupPart(name);
        modifyProductTableViewTop.setItems(p);
        
        try {
            
            int id = Integer.parseInt((modifyProductSearchTxt.getText()));
            modifyProductTableViewTop.getSelectionModel().select(id-1);
        }
        catch (NumberFormatException e){
        
            //Catch the NumberFormat Exception and ignore it 
        }     
    }
    
    /**
     * This method add Parts from the top table view to the bottom table view.  
     * The Parts associated with the Product that was selected. 
     * @param event The Add button located underneath the top table. 
     */
    
    @FXML
    void onActionAddProduct(ActionEvent event) {
        Part selectedPart = modifyProductTableViewTop.getSelectionModel().getSelectedItem();
       
        transferedProductFromMain.addAssociatedPart(selectedPart);
        modifyProductTableViewBottom.setItems(transferedProductFromMain.getAllAssociatedParts());
      
    }
    
   /**
    * This method returns the user back to the Main Form without saving inputted data.
    * Modification or added Parts are not saved. 
    * @param event The Cancel button located on the Modify Product screen.
    */
    
    @FXML
    void onActionCancelProduct(ActionEvent event) throws IOException {
       stage = (Stage)((Button)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
    }
    
    /**
     * This method removes the associatedPart from the bottom table. 
     * The part must be selected to be removed. 
     * @param event The Removed Associated Part button on the Modify Product screen. 
     * RUNTIME ERROR: No errors were produced in the terminal and the program ran without any errors displayed. However, 
     * when trying to remove a product the program would not remove the product correctly or work intermittently. The root cause was the transferedProductFromMain Product variable. 
     * Initially, I had addProducts variable instead of transferedProductFromMain. The addProduct variable was a standalone variable such Product Product5 = new Product(2,"Schwinn
     * EBike",1999.00,15,5,20). Due to this, the program was not referencing the selected Product from MainForm, instead looking at this standalone variable. After updating the variable 
     * and ensuring all controllers communicated properly, the program removed the objects as intended. 
     */
    
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will remove the product. Do you want to continue? ");
          Optional<ButtonType> result = alert.showAndWait();
          
          if (result.isPresent() && result.get() == ButtonType.OK) {
              
          Part selectedPart = modifyProductTableViewBottom.getSelectionModel().getSelectedItem();
              transferedProductFromMain.deleteAssociatedPart(selectedPart);
          }
         
    }

    
    /** This method saves the modified data and tests for logical errors.
     * The modified data is saved to a Product object. The logical errors this method test for is the inventory must not be be more than the maximum number of parts 
     * or minimum number of parts. The minimum must be less than the maximum. The maximum must be less than the minimum.  
     @param event The Save Button located on the Modify Product Screen.
     */
   
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        
        try{
        int countBottomTableParts = 0; 
        int id = Integer.parseInt(modifyProductIdTxt.getText());
        String name = modifyProductNameTxt.getText();
        int stock = Integer.parseInt(modifyProductInvTxt.getText());
        double price = Double.parseDouble(modifyProductPriceTxt.getText());
        int max = Integer.parseInt(modifyProductMaxTxt.getText());
        int min = Integer.parseInt(modifyProductMinTxt.getText());
         
       Inventory.updateProduct(id-1, new Product(id, name, price, stock, min, max));
       
       //Add the associated part of the added Product and Parts 
        for(Part p :modifyProductTableViewBottom.getItems()){
            countBottomTableParts++; 
            Inventory.getAllProducts().get(id-1).addAssociatedPart(p);
       
        }
                
       if((min < max) && (stock < max) && (stock > min)){
       stage = (Stage)((Button)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
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

        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field");
            alert.showAndWait();

    }
        
        
      
    }

    /**
     * Initializes the controller class.
     * Populate the Product Top Table View with Parts data. 
     * Retrieves the Product data through transferedProductFromMain and sets the text fields in the Modify Product form. 
     * Populates the Bottom Table with the Products Associated Part data.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Populate the Product Top Table View with Parts data
        if(transferedProductFromMain != null){
        modifyProductTableViewTop.setItems(Inventory.getAllParts());
        modifyProductPartsIdTopCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameTopCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInvTopCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceTopCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        //Retrieves the Product data through transferedProductFromMain and sets the text fields in the Modify Product form. 
        modifyProductIdTxt.setText(String.valueOf(transferedProductFromMain.getId()));
        modifyProductNameTxt.setText(transferedProductFromMain.getName());
        modifyProductInvTxt.setText(String.valueOf(transferedProductFromMain.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(transferedProductFromMain.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(transferedProductFromMain.getMax()));
        modifyProductMinTxt.setText(String.valueOf(transferedProductFromMain.getMin()));
       
        //Populates the Bottom Table with the Products Associated Part data
        modifyProductTableViewBottom.setItems(transferedProductFromMain.getAllAssociatedParts());
        modifyProductPartsIdBottomCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameBottomCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInvBottomCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceBottomCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        }

        
    }    
    
}
