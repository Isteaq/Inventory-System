/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IAJavaFXMLApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;
import model.Outsourced;
import model.Product;
import model.inHouse;

/**
 *
 * @author istea
 */

/** This class sets up the Main Form screen. */
public class IAJavaFXMLApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
       
       Scene scene = new Scene(root);
       
       stage.setScene(scene);
       stage.show();
    }
    
    
    /** 
    * @param args the command line arguments
    * The is the main method. Starting values have been added. 
    * FUTURE ENHANCEMENT: An added feature that would enhance the program would be to add an exclamation mark exclamation mark
     * next to the Price/Cost per Unit in the Main Part and Products screens when the inventory was low. 
     * This will provide a quick visual check 
     * to ensure the product or parts are not low. 
    */
    
    public static void main(String[] args) {
    
        inHouse Part1 = new inHouse(1,"Brakes",12.99,15,5,20,001);
        inHouse Part2 = new inHouse(2,"Tire",14.99,15,5,20,002);
        Outsourced Part3 = new Outsourced(3,"Rim",56.99,15,5,20,"Custom Wheels");
        
        Product Product4 = new Product(1,"Giant Bicycle",299.99,15,5,20);
        Product Product5 = new Product(2,"Adventure EBike",1999.00,15,5,20);

        Inventory.addPart(Part1);
        Inventory.addPart(Part2);
        Inventory.addPart(Part3);

        Inventory.addProduct(Product4);
        Inventory.addProduct(Product5);
       
        launch(args);
        
    }
    
    
}
