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
 * Initialize the inHouse subclass.
 * Setups the constructor and methods. 
 */
public class inHouse extends Part{
    
    private int machineId; 
    
    /**
    * This method declares a super constructor from the Part parent class
     * @param id The id of the Part 
     * @param name The name of the Part
     * @param price The price of the Part 
     * @param stock The current inventory level of the Part
     * @param min The minimum of that Part that must be in stock. 
     * @param max The maximum of that Part that must be in stock.
     * @param machineId The machineId associated with the inHouse part.  
    */
    public inHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
   
  
    /**
    * This is a setter method used to set the machine id. 
    * @param machineId Takes in an integer and sets it as the machine id. 
    */
    public void setMachine(int machineId){
       this.machineId = machineId;
}
    
 /**
    * This is a getter method used to return the machine id.
     * @return Returns the machine id. 
    */
    public int getMachineId(){
       return machineId;
    }

    

}

