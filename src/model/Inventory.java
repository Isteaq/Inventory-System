/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/** 
 *This class is used to setup the Inventory data.  
 */
public class Inventory {
   
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
 /** 
 * This method is used to add a Part to the allParts ObservableList.
 * @param newPart Takes a Part parameter. 
 */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    /** 
    * This method is used to add a Product to the allProducts ObservableList.
    * @param newProduct Takes a newProduct parameter. 
    */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
   
   /** 
    * This method is used to lookup a Part in the all Parts ObservableList.
    * @param partId  Takes a integer. 
    * @return The Part Object associated with integer.
    */
    public static Part lookupPart(int partId){
        return allParts.get(partId); 
    }
    
   /** 
    * This method is used to lookup a Product in the all Products ObservableList.
    * @param productId  Takes a integer. 
    * @return The Product Object associated with integer. 
    */      
     public static Product lookupProduct(int productId){
         return allProducts.get(productId);
       
    }
     
    /** 
    * This method is used to lookup a Part in the allParts ObservableList.
    * @param partName  Takes a String Part Name. 
    * @return The found Part ObservableList.
    */   
     public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
               
        for(Part p : allParts){
            if(p.getName().contains(partName)){
                foundParts.add(p);
               
            }
        }
         if(foundParts.isEmpty()){
             return allParts;
         }
         
         
         return foundParts;
        
         
         }
    /** 
    * This method is used to lookup a Product in the allProducts ObservableList.
    * @param productName  Takes a String Product Name. 
    * @return The found Product ObservableList.
    */   
      public static ObservableList<Product> lookupProduct(String productName){
          ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        
        for(Product p : allProducts){
            if(p.getName().contains(productName)){
                foundProducts.add(p);
            }
        }
         if(foundProducts.isEmpty()){
             return allProducts;
         }
         
         
         return foundProducts;
        
         
         }
     
   /** 
    * This method is used to update a Part in the allParts ObservableList.
    * @param index  Takes an integer.
    * @param selectedPart Takes a Part object.
    */   
      public static void updatePart(int index,Part selectedPart){
          allParts.set(index, selectedPart);
         
      }
      
   /** 
    * This method is used to delete a Product in the allProducts ObservableList.
    * @param index  Takes an integer.
    * @param newProduct Takes a Product object.
    */  
      public static void updateProduct(int index,Product newProduct){
          
          allProducts.set(index, newProduct);

      }
      
   /** 
    * This method is used to delete a Part in the allParts ObservableList.
    * @param selectedPart Takes a Part object.
    * @return returns true if the element is removed 
    */       
      public static boolean deletePart(Part selectedPart){
          return allParts.remove(selectedPart);
    
      }
      
     
   /** 
    * This method is used to delete a Product in the allProducts ObservableList.
    * @param selectedProduct Takes a Part object.
    * @return returns true if the element is removed 
    */        
      public static boolean deleteProduct(Product selectedProduct){
          return allProducts.remove(selectedProduct);
      }
      
   /** 
    * This method is used to get all the Parts in the allParts ObservableList.
    * @return An ObservableList of all Parts. 
    */  
      public static ObservableList<Part> getAllParts(){
          return allParts;
      }
      
   /** 
    * This method is used to get all the Product in the allProducts ObservableList.
    * @return An ObservableList of all Products. 
    */  
      public static ObservableList<Product> getAllProducts(){
          return allProducts;
      }
      
    
}
