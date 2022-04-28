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
import javafx.collections.FXCollections;
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
import model.inHouse;
import model.Outsourced;

/**
 * FXML Controller class
 *
 * @author istea
 */

/**
 * This class is used to display the starting user interface.
 * Displays Parts and Products available.
 */

public class MainFormController implements Initializable {
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField mainInvPartTxt;

    @FXML
    private TextField mainInvProductsTxt;

    @FXML
    private TableColumn<Part, Integer> mainPartsIdCol;

    @FXML
    private TableColumn<Part, Integer> mainPartsInvCol;

    @FXML
    private TableColumn<Part, String> mainPartsNameCol;

    @FXML
    private TableColumn<Part, Double> mainPartsPriceCol;

    @FXML
    private TableView<Part> mainPartsTableView;

    @FXML
    private TableColumn<Product, Integer> mainProductIdCol;

    @FXML
    private TableColumn<Product, Integer> mainProductInvCol;

    @FXML
    private TableColumn<Product, String> mainProductNameCol;

    @FXML
    private TableColumn<Product, Double> mainProductPriceCol;

    @FXML
    private TableView<Product> mainProductsTableView;
    
    /**
     * This method iterates over a list for Parts and returns the one that matches. 
     * @param id The integer associated with that part. 
     * @return Returns a Part object.
     */
    public static Part selectPart(int id){
        for(Part p : Inventory.getAllParts()){
            if(p.getId() == id)
                return p;
        }
        return null;
    }
    
    /**
     * This method iterates over a list for Products and returns the one that matches. 
     * @param id The integer associated with that products. 
     * @return Returns a Product object.
     */
    public Product selectProduct(int id){
        for(Product p : Inventory.getAllProducts()){
            if(p.getId() == id)
                return p;
        }
        return null;
       
    }

    /**
     * This method loads the Add Part form.
     * @param event The Add button underneath the Parts section on the Main Form. 
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
       stage = (Stage)((Button)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
        
    }
    
    /**
     * This method loads the Add Product form.
     * @param event The Add button underneath the Products section on the Main Form.
     */
    @FXML
    void onActionAddProducts(ActionEvent event) throws IOException {
    
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    
    /**
     * This method deletes the selected Part on the Main Form Screen. 
     * @param event The Delete button underneath the Part section. 
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String name = mainInvPartTxt.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will delete the part. Do you want to continue? ");
        Optional<ButtonType> result = alert.showAndWait();
        
        Part clonePart = null; 
        int cloneId = 0;
        
        for(Part p : Inventory.getAllParts()){
             if(p.getName().contains(name)){
                foundParts.add(p);
                clonePart = p;
        }
        }
        
        try {
            
            int id = Integer.parseInt((mainInvPartTxt.getText()));
            Inventory.deletePart(selectPart(id));
        }
      
        catch (NumberFormatException e){
            //Catch the NumberFormat Exception and ignore it 
          
        }
        if(result.isPresent() && result.get() == ButtonType.OK){
            Inventory.deletePart(clonePart);
            Inventory.deletePart(selectPart(cloneId));
            mainPartsTableView.setItems(Inventory.getAllParts());
        }

        }
    
    /**
     * This method deletes the selected Product on the Main Form Screen. 
     * @param event The Delete button underneath the Product section. 
     */
    @FXML
    void onActionDeleteProducts(ActionEvent event) {
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();
        String name = mainInvProductsTxt.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"This will delete the part. Do you want to continue? ");
        Optional<ButtonType> result = alert.showAndWait();
        Product cloneProduct = null; 
        int cloneId = 0; 
        
        for(Product p : Inventory.getAllProducts()){
             if(p.getName().contains(name)){
                foundProduct.add(p);
                cloneProduct = p;
        }
        }
      
        try {
            
            int id = Integer.parseInt((mainInvProductsTxt.getText()));
            cloneId = id;
      
        }
        
        catch (NumberFormatException e){
         //Catch the NumberFormat Exception and ignore it 
        }
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            if(cloneProduct.getAllAssociatedParts().isEmpty()){
            Inventory.deleteProduct(cloneProduct);
            Inventory.deleteProduct(selectProduct(cloneId));
            mainProductsTableView.setItems(Inventory.getAllProducts());
            }
            else
            {
            Alert associatedPartAlert = new Alert(Alert.AlertType.CONFIRMATION);
            associatedPartAlert.setTitle("Warning Dialog");
            associatedPartAlert.setContentText("This product has associated parts");
            associatedPartAlert.showAndWait();
                
            }
        }
        
        }
        
    
    /**
     * This method closes the program. 
     * @param event The Exit button on the Main Form Screen.
     */
    @FXML
    void onActionExitMain(ActionEvent event) {
        System.exit(0);
    }
    
    
     /**
     * This method loads the Modify Part form.
     * If inHouse Part is selected, the method setTempHolder is called from the ModifyPartFormController. 
     * If Outsourced Part is selected, the method setTempHolder2 is called from the ModifyPartFormController.
     * The partsSelected method is selected based on the chosen radial button which is passed a string to the ModifyPartFormController.  
     * @param event The Modify button underneath the Parts section on the Main Form. 
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        inHouse inHousePart;
        Outsourced outSourcedPart;
           
        try {
          inHousePart = (inHouse) mainPartsTableView.getSelectionModel().getSelectedItem();
          if(inHousePart != null){
          ModifyPartFormController.setTempHolder(inHousePart);
          ModifyPartFormController.partSelected("inHouse");
        }
        }
        
        catch (Exception e){
        outSourcedPart = (Outsourced) mainPartsTableView.getSelectionModel().getSelectedItem();
        if(outSourcedPart != null){
        ModifyPartFormController.setTempHolder2(outSourcedPart);
        ModifyPartFormController.partSelected("outSourced");
        }
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method loads the Modify Product form.
     * @param event The Modify button underneath the Product section on the Main Form. 
     */
    @FXML
    void onActionModifyProducts(ActionEvent event) throws IOException {
        Product selectedProductFromMain;

        selectedProductFromMain =  mainProductsTableView.getSelectionModel().getSelectedItem();
        
        if(selectedProductFromMain != null){

            ModifyProductFormController.setProductFields(selectedProductFromMain);
         
    }
     
       stage = (Stage)((Button)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
        
    }
  
    /**
     * This method searches for a Part in the Main Form Parts table. 
     * String and integers are used for the search parameters. 
     * @param event The Search field above the Parts table. 
     */
   @FXML
    void onActionPartsSearch (ActionEvent event) throws IOException {
        String name = mainInvPartTxt.getText();
        ObservableList<Part> p = Inventory.lookupPart(name);
        mainPartsTableView.setItems(p);
        
        try {
            
            int id = Integer.parseInt((mainInvPartTxt.getText()));
            mainPartsTableView.getSelectionModel().select(selectPart(id));
        }
        catch (NumberFormatException e){
        
            //Catch the NumberFormat Exception and ignore it 
        }     
        }
      
    
    /**
     * This method searches for a Product in the Main Form Product table. 
     * String and integers are used for the search parameters. 
     * @param event The Search field above the Parts table. 
     */
    @FXML
    void onActionProductSearch (ActionEvent event) throws IOException {
        String name = mainInvProductsTxt.getText();
        ObservableList<Product> p = Inventory.lookupProduct(name);
        mainProductsTableView.setItems(p);
        
        try {
            
            int id = Integer.parseInt((mainInvProductsTxt.getText()));
            mainProductsTableView.getSelectionModel().select(selectProduct(id));
        }
        catch (NumberFormatException e){
        
            //Catch the NumberFormat Exception and ignore it 
        }
        
    }

    
    /**
     * Initializes the controller class.
     * Initializes the Parts and Products table with values. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Parts TableView Main Form 
        mainPartsTableView.setItems(Inventory.getAllParts());
        mainPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Products TableView Main Form  
        mainProductsTableView.setItems(Inventory.getAllProducts());
        mainProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       

    }    
    
}
