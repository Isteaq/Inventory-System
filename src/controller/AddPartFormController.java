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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Inventory;
import model.Outsourced;
import model.inHouse;
import model.Part;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author istea
 */


/** This class is used to add Parts. 
 */
public class AddPartFormController implements Initializable {
    Stage stage;
    Parent scene; 
    
    @FXML
    private ToggleGroup AddPartTG;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private TextField addPartCompanyNameTxt;

    @FXML
    private TextField addPartIdTxt;

    @FXML
    private TextField addPartInvTxt;

    @FXML
    private TextField addPartMachineCompanyId;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private RadioButton addPartNoBtn;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private RadioButton addPartYesBtn;
    
    @FXML
    private Label UpdateID;

    /**This method does not save any information. 
       It returns to the user back to the Main Form. 
     * @param event The cancel button.
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
       stage = (Stage)((Button)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
    }
    
    /** This method updates the label to Company Name. 
     * As the Outsourced Part is selected, the label is updated accordingly. 
     @param event The Outsourced button.
     */
    @FXML
    void onActionDisplayCompanyName(ActionEvent event) {
        UpdateID.setText("Company Name");
    }
    
    /** This method updates the label to MachineId. 
     * As the inHouse Part is selected, the label is updated accordingly. 
     @param event The inHouse button.
     */
    @FXML
    void onActionDisplayMachineID(ActionEvent event) {
        UpdateID.setText("Machine ID");
    }
    
    /** This method saves the inputted data and tests for logical errors.
     * The inputted data is saved to a Part object. The logical error this method test for is the inventory must not be be more than the maximum number of parts 
     * or minimum number of parts. The minimum must be less than the maximum. The maximum must be less than the minimum.  
     @param event The Save Button.
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        
        int count = 1; 
        int id = count;
        int stock;
        int max;
        int min;
        int machineId;
        double price;
        String name;
        String companyName;
        
        
        for(Part parts : Inventory.getAllParts()){
            count++;
        }
        
        try
        {
        name = addPartNameTxt.getText();
        stock = Integer.parseInt(addPartInvTxt.getText());
        price = Double.parseDouble(addPartPriceTxt.getText());
        max = Integer.parseInt(addPartMaxTxt.getText());
        min = Integer.parseInt(addPartMinTxt.getText());
        
        if((min < max) && (stock < max) && (stock > min)){
           
           //InHouse Part
        if(addPartYesBtn.isSelected()){
              machineId = Integer.parseInt(addPartMachineCompanyId.getText());
              Inventory.addPart(new inHouse(id,name,price, stock, min, max, machineId));
        }
        
        //Outsourced Part
        else if(addPartNoBtn.isSelected()){
              companyName = addPartMachineCompanyId.getText();
              Inventory.addPart(new Outsourced(id,name,price,stock, min, max,companyName));
        }


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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
