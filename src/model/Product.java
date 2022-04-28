/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author istea
 */

/**
 * Initialize the Product object.
 * Setups the product constructor and methods. 
 */
public class Product{
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id; 
    private String name; 
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     * This method sets up the Product constructor. 
     * @param id The id of the Product. 
     * @param name The name of the Product.
     * @param price The price of the Product. 
     * @param stock The current inventory level of the Product.
     * @param min The minimum of that Product that must be in stock. 
     * @param max The maximum of that Product that must be in stock.
    */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
   /**
    * This is a getter method to get the Product id. 
    * @return returns the Product id.
    */
    public int getId() {
        return id;
    }
    

    /**
    * This is a setter method to set the Product id. 
    * @param id sets the Product id.
    */
    public void setId(int id) {
        this.id = id;
    }

    
    
   /**
    * This is a getter method to get the Product name. 
    * @return returns the Product name.
    */
    public String getName() {
        return name;
    }

    
    /**
    * This is a setter method to set the Product name. 
    * @param name set the product name.
    */
    public void setName(String name) {
        this.name = name;
    }

    
    
   /**
    * This is a getter method to get the Product price. 
    * @return returns the Product price.
    */
    public double getPrice() {
        return price;
    }
    
   
    
   /**
    * This is a setter method to set the Product price. 
    * @param price set the product price.
    */
    public void setPrice(double price) {
        this.price = price;
    }

    
   /**
    * This is a getter method to get the Product current inventory. 
    * @return returns the Product current inventory.
    */
    public int getStock() {
        return stock;
    }

    
   /**
    * This is a setter method to set the Product inventory. 
    * @param stock  set the product current inventory.
    */
    public void setStock(int stock) {
        this.stock = stock;
    }

    
   /**
    * This is a getter method to get the Product minimum inventory level. 
    * @return returns the Product minimum inventory level.
    */
    public int getMin() {
        return min;
    }

    /**
    * This is a setter method to set the Product minimum inventory level. 
    * @param min  set the product minimum inventory level.
    */
    public void setMin(int min) {
        this.min = min;
    }
    
    
  /**
    * This is a getter method to get the Product maximum inventory level. 
    * @return returns the Product maximum inventory level.
    */
    public int getMax() {
        return max;
    }

    
   /**
    * This is a setter method to set the Product maximum inventory level. 
    * @param max  set the product maximum inventory level.
    */
    public void setMax(int max) {
        this.max = max;
    }
    
    
    /**
    * This method adds the Associated Part to the associatedParts Observable List. 
    * @param part takes a Part object.
    */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    
    /**
    * This method deletes the Associated Part from the associatedParts Observable List. 
    * @param selectedAssociatedPart takes a Part object.
     * @return returns true if the selected part is removed. 
    */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
            
        }
        
    /**
    * This method gets all the associated parts from the associatedParts Observable List. 
    * @return associatedParts returns all Associated Parts in the associatedParts Observable List.
    */
     public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
