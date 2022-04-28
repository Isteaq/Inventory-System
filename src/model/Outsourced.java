/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author istea
 */

/**
 * Initialize the Outsourced subclass. 
 * Setups the constructor and methods. 
 */
public class Outsourced extends Part {
    
    private String companyName; 

    /**
    * This method declares a super constructor from the Part parent class
     * @param id The id of the Part.
     * @param name The name of the Part.
     * @param price The price of the Part. 
     * @param stock The current inventory level of the Part.
     * @param min The minimum of that Part that must be in stock. 
     * @param max The maximum of that Part that must be in stock.
     * @param companyName The company Name whose assisted with the Outsourced part. 
    */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
   
   
    
   /**
    * This is a setter method used to set the company name
     * @param companyName takes a String parameter and sets the company name.
    */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
     /**
    * This is a getter method used to return the company name
     * @return Returns the company Name.
    */
    public String getCompanyName() {
        return companyName;
    }

   
    
    

}
