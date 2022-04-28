/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Inventory;
import model.Outsourced;
import model.inHouse;

/**
 * FXML Controller class
 *
 * @author istea
 */

/**
 * This class is used to modify an existing Part. 
 */
public class ModifyPartFormController implements Initializable {
    Stage stage;
    Parent scene;
    private static inHouse tempHolder;
    private static Outsourced tempHolder2;
    private static String partSelected;

    @FXML
    private ToggleGroup ModifyPartTG;

    @FXML
    private TextField modifyPartCompanyNameTxt;

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private RadioButton modifyPartInHouseBtn;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartMachineId;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private RadioButton modifyPartOutsourcedBtn;

    @FXML
    private TextField modifyPartPriceTxt;
    
    @FXML
    private Label UpdateID;
    
    /**
     * This method is used to transfer Part data between the MainForm and the ModifyPartFormController. 
     * @param tempHolder This parameter takes a inHouse Part Object and saves it to a local variable.
     * The local variable is used to populate the Modify Part screen with existing Part data. 
     */
    public static void setTempHolder(inHouse tempHolder) {
        ModifyPartFormController.tempHolder = tempHolder;
    }
    
     /**
     * This method is used to transfer Part data between the MainForm and the ModifyPartFormController. 
     * @param tempHolder2 This parameter takes a Outsourced Part Object and saves it to a local variable.
     * The local variable is used to populate the Modify Part screen with existing Part data. 
     */
    
    public static void setTempHolder2(Outsourced tempHolder2) {
        ModifyPartFormController.tempHolder2 = tempHolder2;
    }
    
     /**
     * This method is used to transfer Part radial button data between the MainForm and the ModifyPartFormController. 
     * @param partSelected This parameter takes a String object and stores it in a local variable.
     * The local variable is then used to select a method in the Initialized based on the input. 
     * @return Returns a string of either InHouse or Outsourced. 
     */
    public static String partSelected(String partSelected){
         return ModifyPartFormController.partSelected = partSelected;
         
    }
    /**
     * This methods updates the the label when Outsourced Part is selected.
     * @param event The Outsourced radial button on the Add Part screen. 
     */
    
    @FXML
    void onActionDisplayCompanyName(ActionEvent event) {
           UpdateID.setText("Company Name");
    }

    /**
     * This methods updates the the label when InHouse Part is selected.
     * @param event The InHouse radial button on the Add Part screen. 
     */
    @FXML
    void onActionDisplayMachineId(ActionEvent event) {
           UpdateID.setText("Machine ID");
    }

     /**
    * This method returns the user back to the Main Form without saving the modified data.
    * Any modified data into the text fields will not be saved. 
    * @param event The Cancel button located on the Add Part screen. 
    */
    @FXML
    void onActionExitPart(ActionEvent event) throws IOException {
       stage = (Stage)((Button)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
    }

      /** This method saves the modified Part data and tests for logical errors.
     * The modified Part data is saved to a Part object. 
     * The logical errors this method test for is the inventory must not be be more than the maximum number of parts or minimum number of parts. 
     * The minimum must be less than the maximum. The maximum must be less than the minimum.  
     @param event The Save Button located on the Add Part Screen. 
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        Integer id; 
        String name = null;
        Integer stock = null;
        Double price = null;
        Integer max = null;
        Integer min = null; 
        Integer machineId = null;
        String companyName = null;
        
        try{
         id = Integer.parseInt(modifyPartIdTxt.getText());
         name = modifyPartNameTxt.getText();
         stock = Integer.parseInt(modifyPartInvTxt.getText());
         price = Double.parseDouble(modifyPartPriceTxt.getText());
         max = Integer.parseInt(modifyPartMaxTxt.getText());
         min = Integer.parseInt(modifyPartMinTxt.getText());
        
        
        //InHouse Part
        if (modifyPartInHouseBtn.isSelected()) {
            machineId = Integer.parseInt(modifyPartMachineId.getText());
            Inventory.updatePart(id-1, new inHouse(id, name, price, stock, min, max, machineId));
            
        } //Outsourced Part
        else if (modifyPartOutsourcedBtn.isSelected()) {
            companyName = modifyPartMachineId.getText();
            Inventory.updatePart(id-1, new Outsourced(id, name, price, stock, min, max, companyName));

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
     * Matches the local variable string of InHouse or Outsourced to populate the Modify Part data. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       if("inHouse".equals(partSelected)) {
       modifyPartInHouseBtn.setSelected(true);
       modifyPartIdTxt.setText(String.valueOf(tempHolder.getId()));
       modifyPartNameTxt.setText(tempHolder.getName());
       modifyPartInvTxt.setText(String.valueOf(tempHolder.getStock()));
       modifyPartPriceTxt.setText(String.valueOf(tempHolder.getPrice()));
       modifyPartMaxTxt.setText(String.valueOf(tempHolder.getMax()));
       modifyPartMinTxt.setText(String.valueOf(tempHolder.getMin()));
       modifyPartMachineId.setText(String.valueOf(tempHolder.getMachineId()));
       }
       
       else if ("outSourced".equals(partSelected)){
        UpdateID.setText("Company Name");
        modifyPartOutsourcedBtn.setSelected(true);
        modifyPartIdTxt.setText(String.valueOf(tempHolder2.getId()));
        modifyPartNameTxt.setText(tempHolder2.getName());
        modifyPartInvTxt.setText(String.valueOf(tempHolder2.getStock()));
        modifyPartPriceTxt.setText(String.valueOf(tempHolder2.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(tempHolder2.getMax()));
        modifyPartMinTxt.setText(String.valueOf(tempHolder2.getMin()));
        modifyPartMachineId.setText(String.valueOf(tempHolder2.getCompanyName()));
       }
       
    }    
    
    }

